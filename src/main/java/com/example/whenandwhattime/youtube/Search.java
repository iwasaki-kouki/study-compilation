/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.whenandwhattime.youtube;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Prints a list of videos based on a search term.
 *
 * @author Jeremy Walker
 */
public class Search {

  /** Global instance of the HTTP transport. */
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();


  /** Global instance of Youtube object to make all API requests. */
  private static YouTube youtube;

  private static String videoid ;
  
  public static String liveschedule;
  
  /**
   * Initializes YouTube object to search for videos on YouTube (Youtube.Search.List). The program
   * then prints the names and thumbnails of each of the videos (only first 50 videos).
   *
   * @param args command line args.
   */
  public static void main(String[] args) {

	  
    try {
      /*
       * The YouTube object is used to make all API requests. The last argument is required, but
       * because we don't need anything initialized when the HttpRequest is initialized, we override
       * the interface and provide a no-op function.
       */
      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {}
      }).setApplicationName("youtube-cmdline-search-sample").build();



      YouTube.Videos.List search = youtube.videos().list("liveStreamingDetails");
      /*
       * It is important to set your API key from the Google Developer Console for
       * non-authenticated requests (found under the Credentials tab at this link:
       * console.developers.google.com/). This is good practice and increased your quota.
       */
      String apiKey ="AIzaSyBRbybJiATFTY9Tr5keqeHVtFBJXQ7XNe8";
      search.setKey(apiKey);
      search.setId(videoid);
      search.setFields("items(kind,liveStreamingDetails/scheduledStartTime)");
      
      VideoListResponse searchResponse = search.execute();
      List<Video> searchResultList = searchResponse.getItems();
      
      if (searchResultList != null) {
          prettyPrint(searchResultList.iterator(), videoid);
          
          
        }
      } catch (GoogleJsonResponseException e) {
        System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
            + e.getDetails().getMessage());
      } catch (IOException e) {
        System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }

  /*
   * Returns a query term (String) from user via the terminal.
   */
  public static void setvideoid(String videoid) throws IOException {
    Search.videoid = videoid;
  }

  /*
   * Prints out all SearchResults in the Iterator. Each printed line includes title, id, and
   * thumbnail.
   *
   * @param iteratorSearchResults Iterator of SearchResults to print
   *
   * @param query Search query (String)
   */
  private static void prettyPrint(Iterator<Video> iteratorSearchResults, String query) {
	      Video singleVideo = iteratorSearchResults.next();
	      liveschedule=singleVideo.getLiveStreamingDetails().getScheduledStartTime().toString();
	      

	      
	      // Double checks the kind is video.
	     
	      System.out.println(" scheduledStartTime: " + singleVideo.getLiveStreamingDetails().getScheduledStartTime());
	      System.out.println("\n-------------------------------------------------------------\n");
  }
	      	
}