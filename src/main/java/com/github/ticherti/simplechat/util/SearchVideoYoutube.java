package com.github.ticherti.simplechat.util;

import com.github.ticherti.simplechat.entity.Videos;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SearchVideoYoutube {

    private final String apikey;
    private YouTube youTube;

    public SearchVideoYoutube(@Value("${youtube.apikey}") String apikey) {
        if (youTube == null) {
            youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
            }).setApplicationName("simple-chat").build();
        }
        this.apikey = apikey;
    }

    public String getChannelId(String channel) throws Throwable {
        String channelId = null;
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        search.setQ(channel);
        search.setType("channel");
        search.setMaxResults(50l);

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
            while (iteratorSearchResults.hasNext()) {
                SearchResult singleChannel = iteratorSearchResults.next();
                if (singleChannel.getId().getKind().equals("youtube#channel") & singleChannel.getSnippet().getTitle().equals(channel)) {
                    channelId = singleChannel.getId().getChannelId();
                    break;
                }
            }
        }
        return channelId;
    }

    public Map<String, String> getNameAndCommentMap(Videos video) throws Throwable {
        YouTube.CommentThreads.List commentsList = youTube.commentThreads().list("snippet");
        int maxResults = 100;
        CommentThreadListResponse videoCommentListResponse = commentsList.setKey(apikey).setVideoId(video.getId())
                .setMaxResults((long) maxResults).setTextFormat("plainText").execute();
        List<CommentThread> videoComments = videoCommentListResponse.getItems();

        int random = new Random().nextInt(maxResults);
        CommentSnippet snippet = videoComments.get(random).getSnippet().getTopLevelComment().getSnippet();

        return Collections.singletonMap(snippet.getAuthorDisplayName(), snippet.getTextDisplay());
    }

    public List<Videos> getVideoList(String movie, String channelId, Long resultCount, Boolean sort) throws Throwable {
        List<Videos> videosList = new ArrayList<>();

        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        if (!movie.isEmpty()) {
            search.setQ(movie);
        }

        search.setType("video");

        if (sort) {
            search.setOrder("date");
        }

        if (!channelId.isEmpty()) {
            search.setChannelId(channelId);
        }

        search.setFields("items(id/kind, id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 0) {
            search.setMaxResults(resultCount);
        }

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
            while (iteratorSearchResults.hasNext()) {
                SearchResult singleVideo = iteratorSearchResults.next();
                ResourceId rId = singleVideo.getId();

                if (rId.getKind().equals("youtube#video")) {

                    Videos videos = new Videos();
                    videos.setId(rId.getVideoId());
                    videos.setTitle(singleVideo.getSnippet().getTitle());
                    videos.setPublished(new Date(singleVideo.getSnippet().getPublishedAt().getValue()));
                    YouTube.Videos.List youtubeVideos = youTube.videos()
                            .list("id,snippet,player,contentDetails,statistics").setId(rId.getVideoId());
                    youtubeVideos.setKey(apikey);
                    VideoListResponse videoListResponse = youtubeVideos.execute();

                    if (!videoListResponse.getItems().isEmpty()) {
                        Video video = videoListResponse.getItems().get(0);
                        videos.setViewCount(video.getStatistics().getViewCount().longValue());
                        videos.setLikeCount(video.getStatistics().getLikeCount().longValue());
                    }
                    videosList.add(videos);
                }
            }
        }
        return videosList;
    }

    public List<Videos> getVideoList(String channelId, Long resultCount, Boolean sort) throws Throwable {
        List<Videos> videosList = new ArrayList<>();

        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
//        if (!movie.isEmpty()) {
//            search.setQ(movie);
//        }

        search.setType("video");

        if (sort) {
            search.setOrder("date");
        }

        if (!channelId.isEmpty()) {
            search.setChannelId(channelId);
        }

        search.setFields("items(id/kind, id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 0) {
            search.setMaxResults(resultCount);
        }

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
            while (iteratorSearchResults.hasNext()) {
                SearchResult singleVideo = iteratorSearchResults.next();
                ResourceId rId = singleVideo.getId();

                if (rId.getKind().equals("youtube#video")) {

                    Videos videos = new Videos();
                    videos.setId(rId.getVideoId());
                    videos.setTitle(singleVideo.getSnippet().getTitle());
                    videos.setPublished(new Date(singleVideo.getSnippet().getPublishedAt().getValue()));
                    YouTube.Videos.List youtubeVideos = youTube.videos()
                            .list("id,snippet,player,contentDetails,statistics").setId(rId.getVideoId());
                    youtubeVideos.setKey(apikey);
                    VideoListResponse videoListResponse = youtubeVideos.execute();

                    if (!videoListResponse.getItems().isEmpty()) {
                        Video video = videoListResponse.getItems().get(0);
                        videos.setViewCount(video.getStatistics().getViewCount().longValue());
                        videos.setLikeCount(video.getStatistics().getLikeCount().longValue());
                    }
                    videosList.add(videos);
                }
            }
        }
        return videosList;
    }
}
