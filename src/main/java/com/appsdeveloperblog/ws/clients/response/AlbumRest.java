package com.appsdeveloperblog.ws.clients.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AlbumRest {
    private String userId;
    private String albumId;
    private String albumTitle;
    private String albumDescription;
    private String albumUrl;
}
