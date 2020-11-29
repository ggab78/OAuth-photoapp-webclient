package com.appsdeveloperblog.ws.clients.controller;

import com.appsdeveloperblog.ws.clients.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                oAuth2Token.getAuthorizedClientRegistrationId(),
                oAuth2Token.getName());

        String jwtAccessToken=client.getAccessToken().getTokenValue();
        System.out.println("JWT : "+ jwtAccessToken);

        System.out.println("Principal "+principal);
        OidcIdToken idToken=principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("Id token value "+idTokenValue);

        String url="http://localhost:8082/albums";
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+jwtAccessToken);
        HttpEntity entity=new HttpEntity(headers);

        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<List<AlbumRest>> responseEntity=restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>() {});


        List<AlbumRest> albums=new ArrayList<>();
        albums=responseEntity.getBody();

//        AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build();
//        albums.add(AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build());
//        albums.add(AlbumRest.builder().albumTitle("second").albumUrl("http://album2").build());






        model.addAttribute("albums", albums);

        return "albums";
    }

}
