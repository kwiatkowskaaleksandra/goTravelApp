package biuropodrozy.gotravel.security.oauth2;

import biuropodrozy.gotravel.role.Role;
import biuropodrozy.gotravel.role.RoleEnum;
import biuropodrozy.gotravel.role.RoleRepository;
import biuropodrozy.gotravel.security.services.UserDetailsImpl;
import biuropodrozy.gotravel.user.User;
import biuropodrozy.gotravel.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The CustomOAuth2UserService class extends the DefaultOAuth2UserService to provide custom behavior for loading and updating user details using OAuth2 authentication.
 */
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    /**
     * The service responsible for managing user-related operations.
     */
    public final UserService userService;

    /**
     * Repository for managing user roles.
     */
    private final RoleRepository roleRepository;

    /**
     * The list of OAuth2UserInfoExtractor implementations for extracting user information from different OAuth2 providers.
     */
    private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

    /**
     * Constructs a new CustomOAuth2UserService with the specified dependencies.
     *
     * @param userService             the service for managing users
     * @param roleRepository          the repository for managing user roles
     * @param oAuth2UserInfoExtractors the list of OAuth2 user info extractors
     */
    @Autowired
    public CustomOAuth2UserService(@Lazy UserService userService, RoleRepository roleRepository, List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.oAuth2UserInfoExtractors = oAuth2UserInfoExtractors;
    }

    /**
     * Upserts user details based on the provided UserDetailsImpl.
     *
     * @param userDetails The UserDetailsImpl containing user details.
     * @return The updated User object.
     */
    private User upsertUser(UserDetailsImpl userDetails) {
        Optional<User> userOptional = userService.getUserByUsername(userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        User user;

        if (userOptional.isEmpty()) {
            user = new User();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setFirstname(userDetails.getFirstname());
            user.setProvider(userDetails.getProvider());
            user.setRoles(userRoleSet);
            user.setActivity(true);
        } else {
            user = userOptional.get();
            user.setEmail(userDetails.getEmail());
        }
        userService.save(user);

        return user;
    }

    /**
     * Loads user details using OAuth2 authentication, including extracting user info and updating the user details if necessary.
     *
     * @param userRequest The OAuth2UserRequest containing user request details.
     * @return The UserDetailsImpl object containing loaded user details.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional = oAuth2UserInfoExtractors.stream()
                .filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.accepts(userRequest))
                .findFirst();

        if (oAuth2UserInfoExtractorOptional.isEmpty()) {
            throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet.");
        }

        UserDetailsImpl userDetails = oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
        User user = upsertUser(userDetails);
        userDetails.setId(user.getId());
        return userDetails;
    }
}
