package com.appsdeveloperblog.ws.clients.controller;

import com.appsdeveloperblog.ws.clients.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                oAuth2Token.getAuthorizedClientRegistrationId(),
                oAuth2Token.getName());

        System.out.println("JWT : "+ client.getAccessToken().getTokenValue());

        System.out.println("Principal "+principal);
        OidcIdToken idToken=principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("Id token value "+idTokenValue);


        List<AlbumRest> albums=new ArrayList<>();

        AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build();
        albums.add(AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build());
        albums.add(AlbumRest.builder().albumTitle("second").albumUrl("http://album2").build());

        model.addAttribute("albums", albums);

        return "albums";
    }

}
