package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.Trip;
import biuropodrozy.gotravel.repository.TripRepository;
import biuropodrozy.gotravel.rest.dto.request.TripFilteringRequest;
import biuropodrozy.gotravel.service.TripService;
import biuropodrozy.gotravel.service.impl.tripRecommendation.FuzzyLogicService;
import biuropodrozy.gotravel.service.impl.tripRecommendation.TripMatcher;
import biuropodrozy.gotravel.model.UserTripPreferences;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Trip service implementation.
 */
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    /**
     * Repository for accessing and managing Trip entities.
     */
    private final TripRepository tripRepository;

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

    /**
     * Get all trips.
     *
     * @return list of trips
     */
    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    /**
     * Get by id trip.
     *
     * @param idTrip the id trip
     * @return the trip
     */
    @Override
    public Trip getTripByIdTrip(final Long idTrip) {
        return tripRepository.findByIdTrip(idTrip);
    }

    /**
     * Retrieves trips by type of trip with pagination.
     * If typeOfTrip is "search", returns all trips.
     *
     * @param typeOfTrip The type of trip to filter.
     * @param pageable   Pagination information.
     * @return Page of trips filtered by typeOfTrip.
     */
    @Override
    public Page<Trip> getTripsByTypeOfTrip(String typeOfTrip, Pageable pageable) {
        if (typeOfTrip.equals("search")) {
            List<Trip> allTrips = tripRepository.findAll();

            int pageSize = pageable.getPageSize();
            int pageNumber = pageable.getPageNumber();

            int start = pageNumber * pageSize;
            int end = Math.min(start + pageSize, allTrips.size());

            List<Trip> pageContent = allTrips.subList(start, Math.min(end, allTrips.size()));

            return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), allTrips.size());
        }
        return tripRepository.findAllByTypeOfTrip_Name(typeOfTrip, pageable);
    }

    /**
     * Counts the number of trips by type of trip.
     * If typeOfTrip is "search", returns the count of all trips.
     *
     * @param typeOfTrip The type of trip to count.
     * @return The count of trips filtered by typeOfTrip.
     */
    @Override
    public int countTripByTypeOfTrip(String typeOfTrip) {
        return typeOfTrip.equals("search") ? tripRepository.findAll().size() : tripRepository.countTripByTypeOfTrip_Name(typeOfTrip);
    }

    /**
     * Filters trips based on the provided criteria.
     *
     * @param filteringRequest The filtering criteria for trips.
     * @param pageable         Pagination information.
     * @return Page of filtered trips based on the provided criteria.
     */
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
        int pageIndex = pageNumber - 1;

        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalElements);

        List<Trip> pageContent = tripList.subList(start, Math.min(end, totalElements));

        return new PageImpl<>(pageContent, PageRequest.of(pageIndex, pageSize), totalElements);
    }

    /**
     * Generates trip recommendations based on user preferences.
     *
     * @param userPreferences The user's trip preferences.
     * @return A list of recommended trips.
     */
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

    /**
     * Retrieves the most booked trips.
     *
     * @return A list of the most booked trips.
     */
    @Override
    public List<Trip> getMostBookedTrips() {
        Pageable topFive = PageRequest.of(0,5);
        List<Object[]> mostBookedTripsResult = tripRepository.findTop5MostBookedTrips(topFive);
        return mostBookedTripsResult.stream()
                .map(result -> (Trip) result[0])
                .collect(Collectors.toList());
    }
}
