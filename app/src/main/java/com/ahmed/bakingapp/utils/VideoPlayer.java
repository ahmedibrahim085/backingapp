package com.ahmed.bakingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class VideoPlayer implements Player.EventListener {

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private static SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;
    private static MediaSource mediaSource;

    private static int currentWindow;
    private static long playbackPosition;
    private static boolean playWhenReady;

    private static TrackSelector trackSelector;
    private static LoadControl loadControl;


    // Initialize the player.
    public static void initializePlayer(Uri mediaUri, PlayerView mPlayerView, Activity context) {
        if ( getmExoPlayer() == null ) {
            // Create an instance of the ExoPlayer.
            trackSelector = new DefaultTrackSelector();
            loadControl = new DefaultLoadControl();
            setmExoPlayer(ExoPlayerFactory.newSimpleInstance(context, trackSelector,
                    loadControl));
            // Set the ExoPlayer.EventListener to this activity.
            getmExoPlayer().addListener(eventListener);
            mPlayerView.setPlayer(getmExoPlayer());
            setPlayerMediaSource(context, mediaUri);
        }
    }

    public static void setPlayerMediaSource(Context context, Uri mediaUri) {
        String userAgent = "exoplayer-bakingapp";
        mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                context, userAgent), new DefaultExtractorsFactory(), null, null);
        getmExoPlayer().prepare(mediaSource);
        getmExoPlayer().setPlayWhenReady(true);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    public static void initializeMediaSession(Activity activity) {

        // Create a MediaSessionCompat.
        setmMediaSession(new MediaSessionCompat(activity, TAG));

        // Enable callbacks from MediaButtons and TransportControls.
        getmMediaSession().setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        getmMediaSession().setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                        | PlaybackStateCompat.ACTION_PLAY_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );

        getmMediaSession().setPlaybackState(mStateBuilder.build());
        // MySessionCallback has methods that handle callbacks from a media controller.
        getmMediaSession().setCallback(mediasSessionCallback);
        // Start the Media Session since the activity is active.
        getmMediaSession().setActive(true);
    }

    public static void releasePlayer() {
        Log.d(TAG, "releasePlayer");
        if ( getmExoPlayer() != null ) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            getmExoPlayer().release();
            setmExoPlayer(null);
        }
    }

    public static void stopPlayer() {
        if ( VideoPlayer.getmExoPlayer() != null ) {
            VideoPlayer.getmExoPlayer().stop();
        }
    }

    public static MediaSessionCompat getmMediaSession() {
        return mMediaSession;
    }

    public static void setmMediaSession(MediaSessionCompat mMediaSession) {
        VideoPlayer.mMediaSession = mMediaSession;
    }

    public static SimpleExoPlayer getmExoPlayer() {
        return VideoPlayer.mExoPlayer;
    }

    private static void setmExoPlayer(SimpleExoPlayer mExoPlayer) {
        VideoPlayer.mExoPlayer = mExoPlayer;
    }

    private static Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            Log.d(TAG, "eventListener - timeline : " + timeline);
            Log.d(TAG, "eventListener - manifest : " + manifest);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.d(TAG, "onPlayerStateChanged - playWhenReady : " + playWhenReady);
            Log.d(TAG, "onPlayerStateChanged - playbackState : " + playbackState);
            switch (playbackState) {
                case PlaybackStateCompat.STATE_PLAYING:
                    Log.d(TAG, "playbackState : STATE_PLAYING");
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    Log.d(TAG, "playbackState : STATE_PAUSED");
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    Log.d(TAG, "playbackState : STATE_STOPPED");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                    Log.d(TAG, "playbackState : STATE_SKIPPING_TO_NEXT");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                    Log.d(TAG, "playbackState : STATE_SKIPPING_TO_PREVIOUS");
                    break;
                case PlaybackStateCompat.STATE_FAST_FORWARDING:
                    Log.d(TAG, "playbackState : STATE_FAST_FORWARDING");
                    break;
                case PlaybackStateCompat.STATE_REWINDING:
                    Log.d(TAG, "playbackState : STATE_REWINDING");
                    break;
                default:
                    Log.d(TAG, "playbackState : STATE_NONE");
                    break;
            }
        }
    };

    private static MediaSessionCompat.Callback mediasSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "mediasSessionCallback - onPause");
//            pause();
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "mediasSessionCallback - onStop");
//            stop();

        }

        @Override
        public void onPlay() {
            super.onPlay();
            Log.d(TAG, "mediasSessionCallback - onPlay");
//            resume();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            Log.d(TAG, "mediasSessionCallback - onSkipToNext");
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            Log.d(TAG, "mediasSessionCallback - onSkipToPrevious");
        }

        @Override
        public void onFastForward() {
            super.onFastForward();
            Log.d(TAG, "mediasSessionCallback - onFastForward");
        }


        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            Log.d(TAG, "mediasSessionCallback - mediaButtonEvent : " + mediaButtonEvent);
            return super.onMediaButtonEvent(mediaButtonEvent);
        }
    };


    // ===================================================================================


}
