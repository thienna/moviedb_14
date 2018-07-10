package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.data.model.Trailer;
import com.nganhthien.mikemovie.data.source.SearchDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchSearchResultFromUrl extends BaseFetchDataFromUrl<List<Object>> {
    private SearchDataSource.OnFetchSearchResultListener mListener;

    public FetchSearchResultFromUrl(SearchDataSource.OnFetchSearchResultListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Object> doInBackground(String... strings) {
        try {
            return extractDataFromJson(getJsonStringFromUrl(strings[0]));
        } catch (JSONException e) {
            mWrapperException.setException(e);
        } catch (IOException e) {
            mWrapperException.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> dataList) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchSearchResultSuccess(dataList);
        } else {
            mListener.onFetchSearchResultsFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Object> extractDataFromJson(String dataJSON) throws JSONException {
        List<Object> dataList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Trailer.JsonKey.RESULTS);
        for (int i = 0; i < resultsArray.length(); i++) {
            // Current cursor
            JSONObject currentItem = resultsArray.getJSONObject(i);

            switch (currentItem.optString(Constants.MovieApi.JSON_KEY_MEDIA_TYPE)) {
                case Constants.MovieApi.JSON_KEY_MEDIA_TYPE_MOVIE:
                    movieList.add(makeMovieFromJson(currentItem));
                    break;
                case Constants.MovieApi.JSON_KEY_MEDIA_TYPE_PERSON:
                    personList.add(makePersonFromJson(currentItem));
                    break;
            }
        }

        dataList.addAll(movieList);
        dataList.addAll(personList);
        return dataList;
    }

    private Movie makeMovieFromJson(JSONObject currentItem) {
        int id = currentItem.optInt(Movie.JsonKey.ID);
        String title = currentItem.optString(Movie.JsonKey.TITLE);
        String overview = currentItem.optString(Movie.JsonKey.OVERVIEW);
        int voteAverage = currentItem.optInt(Movie.JsonKey.VOTE_COUNT);
        String posterPath = currentItem.optString(Movie.JsonKey.POSTER_PATH);
        String backdropPath = currentItem.optString(Movie.JsonKey.BACKDROP_PATH);
        String releaseDate = currentItem.optString(Movie.JsonKey.RELEASE_DATE);

        Movie movie = new Movie(id, title);
        movie.setOverview(overview);
        movie.setVoteAverage(voteAverage);
        movie.setPosterImage(posterPath);
        movie.setBackdropImage(backdropPath);
        movie.setReleaseDate(releaseDate);

        return movie;
    }

    private Person makePersonFromJson(JSONObject currentItem) {
        String name = currentItem.optString(Person.JsonKey.NAME);
        String popularity = currentItem.optString(Person.JsonKey.POPULARITY);
        String profilePath = currentItem.optString(Person.JsonKey.PROFILE_PATH);
        String id = currentItem.optString(Person.JsonKey.ID);

        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setPopularity(popularity);
        person.setProfilePath(profilePath);

        return person;
    }
}
