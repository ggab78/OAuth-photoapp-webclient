package com.appsdeveloperblog.ws.clients.controller;

import com.appsdeveloperblog.ws.clients.response.AlbumRest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumsController {

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal){


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
