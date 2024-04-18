package biuropodrozy.gotravel.trip;

import biuropodrozy.gotravel.accommodation.AccommodationService;
import biuropodrozy.gotravel.attraction.Attraction;
import biuropodrozy.gotravel.attraction.AttractionService;
import biuropodrozy.gotravel.exception.TripManagementException;
import biuropodrozy.gotravel.insurance.InsuranceService;
import biuropodrozy.gotravel.photo.Photo;
import biuropodrozy.gotravel.photo.PhotoService;
import biuropodrozy.gotravel.transport.TransportService;
import biuropodrozy.gotravel.trip.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.trip.tripRecommendation.FuzzyLogicService;
import biuropodrozy.gotravel.trip.tripRecommendation.TripMatcher;
import biuropodrozy.gotravel.userTripPreferences.UserTripPreferences;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link TripService} interface.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TripServiceImpl implements TripService {

    /**
     * Repository for accessing and managing Trip entities.
     */
    private final TripRepository tripRepository;

    /**
     * Service for managing attractions.
     */
    private final AttractionService attractionService;

    /**
     * Service for managing photos.
     */
    private final PhotoService photoService;

    /**
     * Service for managing insurances.
     */
    private final InsuranceService insuranceService;

    /**
     * Service for managing accommodations.
     */
    private final AccommodationService accommodationService;

    /**
     * Service for managing transports.
     */
    private final TransportService transportService;

    /**
     * Service for evaluating user preferences using fuzzy logic.
     */
    private final FuzzyLogicService fuzzyLogicService;

    /**
     * Service for matching trips with user preferences.
     */
    private final TripMatcher tripMatcher;

    /**
     * Maximum price of the trip.
     */
    private static final int MAX_PRICE = 100000000;

    /**
     * Maximum number of days value.
     */
    private static final int MAX_DAYS = 10000;

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getTripByIdTrip(final Long idTrip) {
        return tripRepository.findByIdTrip(idTrip);
    }

    @Override
    public Page<Trip> getTripsByTypeOfTrip(String typeOfTrip, Pageable pageable) {
        if (typeOfTrip.equals("search") || typeOfTrip.equals("all")) {
            List<Trip> allTrips = getAllTrips();

            int pageSize = pageable.getPageSize();
            int pageNumber = pageable.getPageNumber();

            int start = pageNumber * pageSize;
            int end = Math.min(start + pageSize, allTrips.size());

            List<Trip> pageContent = allTrips.subList(start, Math.min(end, allTrips.size()));

            return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), allTrips.size());
        }
        return tripRepository.findAllByTypeOfTrip_Name(typeOfTrip, pageable);
    }

    @Override
    public int countTripByTypeOfTrip(String typeOfTrip) {
        return (typeOfTrip.equals("search") || typeOfTrip.equals("all")) ? tripRepository.findAll().size() : tripRepository.countTripByTypeOfTrip_Name(typeOfTrip);
    }

    @Override
    public Page<Trip> filteringTrips(TripFilteringRequest filteringRequest, Pageable pageable) {
        List<Trip> tripList = getAllTrips();

        if (filteringRequest.getTypeOfTrip() != null && !filteringRequest.getTypeOfTrip().equals("search")) {
            tripList = tripList.stream().filter(t -> t.getTypeOfTrip().getName().equals(filteringRequest.getTypeOfTrip())).collect(Collectors.toList());
        }

        if (filteringRequest.getIdCountry() != 0) {
            tripList = tripList.stream().filter(t -> t.getTripCity().getCountry().getIdCountry() == filteringRequest.getIdCountry()).collect(Collectors.toList());
        }

        if (filteringRequest.getTypeOfTransport() != 0) {
            tripList = tripList.stream().filter(t -> t.getTripTransport().getIdTransport() == filteringRequest.getTypeOfTransport()).collect(Collectors.toList());
        }

        if (filteringRequest.getMinPrice() != 0 || filteringRequest.getMaxPrice() != 0) {
            if (filteringRequest.getMinPrice() != 0 && filteringRequest.getMaxPrice() == 0) {
                tripList = tripList.stream().filter(t -> t.getPrice() >= filteringRequest.getMinPrice() && t.getPrice() <= MAX_PRICE).collect(Collectors.toList());
            } else {
                tripList = tripList.stream().filter(t -> t.getPrice() >= filteringRequest.getMinPrice() && t.getPrice() <= filteringRequest.getMaxPrice()).collect(Collectors.toList());
            }
        }

        if (filteringRequest.getMinDays() != 0 || filteringRequest.getMaxDays() != 0) {
            if (filteringRequest.getMinDays() != 0 && filteringRequest.getMaxDays() == 0) {
                tripList = tripList.stream().filter(t -> t.getNumberOfDays() >= filteringRequest.getMinDays() && t.getNumberOfDays() <= MAX_DAYS).collect(Collectors.toList());
            } else {
                tripList = tripList.stream().filter(t -> t.getNumberOfDays() >= filteringRequest.getMinDays() && t.getNumberOfDays() <= filteringRequest.getMaxDays()).collect(Collectors.toList());
            }
        }

        int totalElements = tripList.size();
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int pageIndex = Math.max(0, pageNumber - 1);

        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalElements);

        List<Trip> pageContent = tripList.subList(start, Math.min(end, totalElements));

        return new PageImpl<>(pageContent, PageRequest.of(pageIndex, pageSize), totalElements);
    }

    @Override
    public List<Trip> tripRecommendation(UserTripPreferences userPreferences) {
        double score = fuzzyLogicService.evaluateUserPreferences(userPreferences);
        List<Trip> potentialTrips = tripRepository.findAll();
        double minPrice = tripRepository.findFirstByOrderByPriceAsc().getPrice();
        double maxPrice = tripRepository.findFirstByOrderByPriceDesc().getPrice();

        return potentialTrips.stream()
                .filter(trip -> tripMatcher.matchTripWithScore(trip, minPrice, maxPrice, score, userPreferences))
                .toList();
    }

    @Override
    public List<Trip> getMostBookedTrips() {
        Pageable topFive = PageRequest.of(0,5);
        List<Object[]> mostBookedTripsResult = tripRepository.findTop5MostBookedTrips(topFive);
        return mostBookedTripsResult.stream()
                .map(result -> (Trip) result[0])
                .collect(Collectors.toList());
    }

    @Override
    public void saveTrip(Trip trip) {
        validateTripOffer(trip);

        Trip newTrip;

        if (trip.getIdTrip() != 0) {
            newTrip = getTripByIdTrip(trip.getIdTrip());
        } else newTrip = new Trip();

        newTrip.setTripCity(trip.getTripCity());
        newTrip.setPrice(trip.getPrice());
        newTrip.setTripTransport(trip.getTripTransport());
        newTrip.setTripAccommodation(trip.getTripAccommodation());
        newTrip.setFood(trip.getFood());
        newTrip.setTypeOfTrip(trip.getTypeOfTrip());
        newTrip.setNumberOfDays(trip.getNumberOfDays());
        newTrip.setTripDescription(trip.getTripDescription());
        newTrip.setActivityLevel(trip.getActivityLevel());

        if (trip.getTripAttraction().size() > 0) {
            newTrip.setTripAttraction(
                    trip.getTripAttraction().stream()
                            .map(attraction -> attractionService.getAttractionById(attraction.getIdAttraction()))
                            .collect(Collectors.toSet())
            );
        }

        trip.getTripPhoto().stream()
                .findFirst()
                .map(Photo::getUrlPhoto)
                .ifPresent(newTrip::setRepresentativePhoto);

        if (trip.getIdTrip() != 0) {
            tripRepository.save(newTrip);
            savePhotos(trip.getTripPhoto(), newTrip);
        } else {
            Trip savedTrip = tripRepository.save(newTrip);
            savePhotos(trip.getTripPhoto(), savedTrip);
        }
    }

    @Override
    public double validate(Trip trip) {
        validateTripOffer(trip);

        double totalPrice = 1000.0;

        totalPrice += accommodationService.getAccommodationsById(trip.getTripAccommodation().getIdAccommodation()).getPriceAccommodation() * trip.getNumberOfDays();
        totalPrice += transportService.getTransportById(trip.getTripTransport().getIdTransport()).getPriceTransport() * 2;
        if (!trip.getFood().equals("none")) totalPrice += trip.getNumberOfDays() * 70;
        if (trip.getTripAttraction().size() > 0)  {
            for (Attraction attraction : trip.getTripAttraction()) {
                totalPrice += attractionService.getAttractionById(attraction.getIdAttraction()).getPriceAttraction();
            }
        }
        totalPrice += insuranceService.getAll().get(0).getPrice();
        return totalPrice;
    }

    @Override
    public void deleteTheOffer(Long idTrip) {
        photoService.deletePhotoForTrip(getTripByIdTrip(idTrip));
        tripRepository.delete(getTripByIdTrip(idTrip));
    }

    /**
     * Saves photos for a trip, deleting existing ones if any.
     *
     * @param photos the set of photos to be saved
     * @param trip   the trip to which the photos belong
     */
    private void savePhotos(Set<Photo> photos, Trip trip) {
        photoService.deletePhotoForTrip(trip);
        photos.forEach(photo -> {
            photo.setTrip(trip);
            photoService.savePhoto(photo);
        });
    }

    /**
     * Validates various aspects of a trip offer.
     *
     * @param trip the trip to be validated
     * @throws TripManagementException if the trip data is incorrect or incomplete
     */
    private void validateTripOffer(Trip trip) {

        if (trip.getTripCity().getIdCity() == 0 || trip.getTripTransport().getIdTransport() == 0 || trip.getTripAccommodation().getIdAccommodation() == 0 ||
                trip.getTypeOfTrip().getIdTypeOfTrip() == 0) {
            log.error("Incorrectly entered data regarding the city, transport, accommodation or offer type.");
            throw new TripManagementException("incorrectlyEnteredData");
        }

        if (trip.getFood().equals("")) {
            log.error("Food data entered incorrectly.");
            throw new TripManagementException("incorrectlyEnteredDataFood");
        }

        if (trip.getNumberOfDays() == 0) {
            log.error("Incorrectly entered data regarding the duration of the trip.");
            throw new TripManagementException("incorrectlyEnteredDataNumberOfDays");
        }

        if (trip.getTripDescription().equals("") || trip.getTripDescription().length() < 10) {
            log.error("Incorrectly entered data regarding the duration of the trip.");
            throw new TripManagementException("incorrectlyEnteredDataDescription");
        }

        if (trip.getTripPhoto().size() == 0) {
            log.error("Error. No photos.");
            throw new TripManagementException("incorrectlyEnteredDataTripPhoto");
        } else {
            for (Photo photo: trip.getTripPhoto()) {
                if (photo.getUrlPhoto().equals("")) throw new TripManagementException("incorrectlyEnteredDataTripPhoto");
            }
        }
    }
}
