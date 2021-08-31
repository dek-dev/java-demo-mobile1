package com.oppo.marketdemo.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;

import com.google.android.exoplayer2.BuildConfig;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.oppo.marketdemo.R;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/6/4 15:35
 * Description:
 */
public class ExoVideoView extends FrameLayout {

    private Context mContext;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private SimpleExoPlayer mSimpleExoPlayer;
    private MediaSource mediaSource;
    private ArrayList<Player.EventListener> eventListeners;
    private ArrayList<AnalyticsListener> analyticsListeners;

    private ImageView videoBg;
    private PlayerView mPlayerView;

    private int rePlayCount;
    private int mVideoId;

    public ExoVideoView(Context context) {
        this(context, null);
    }

    public ExoVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("HandlerLeak")
    public ExoVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        eventListeners = new ArrayList<>();
        analyticsListeners = new ArrayList<>();
        rePlayCount = 1;
        LayoutParams fl = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        videoBg = new ImageView(mContext);
        videoBg.setLayoutParams(fl);
        videoBg.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(videoBg, 0);
//        mPlayerView = new PlayerView(mContext);
        mPlayerView = (PlayerView) LayoutInflater.from(mContext).inflate(R.layout.player_view, null, false);
        mPlayerView.setLayoutParams(fl);
        mPlayerView.setKeepContentOnPlayerReset(false);
        mPlayerView.setShutterBackgroundColor(mContext.getColor(R.color.colorTransparent));
        mPlayerView.setUseController(false);
        mPlayerView.setVisibility(INVISIBLE);
        addView(mPlayerView, 1);
    }

    public void setResource(@DrawableRes int imageId, @RawRes int videoId){
        setVideoBackground(imageId);
        setVideoId(videoId);
    }

    public void setVideoBackground(@DrawableRes int imageId){
        videoBg.setImageResource(imageId);
    }

    public void setVideoId(@RawRes int videoId){
        mVideoId = videoId;
        mediaSource = new LoopingMediaSource(createMediaSource(mVideoId), rePlayCount);
    }

    public void start(){
        initializePlayer();
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(true);
            mSimpleExoPlayer.prepare(mediaSource);
        }
    }

    public void resume(){
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    public void pause(){
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    public void reset(){
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.seekTo(mSimpleExoPlayer.getCurrentWindowIndex(), 0);
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    public void seekTo(long time){
        if (mSimpleExoPlayer != null) {
            seekTo(mSimpleExoPlayer.getCurrentWindowIndex(), time);
        }
    }

    public void seekTo(int windowIndex, long time){
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.seekTo(windowIndex, time);
        }
    }

    public void setRePlayCount(int rePlayCount) {
        this.rePlayCount = rePlayCount;
        if (mVideoId != 0) {
            mediaSource = new LoopingMediaSource(createMediaSource(mVideoId), rePlayCount);
        }
    }

    public long getCurrentPosition(){
        if (mSimpleExoPlayer != null){
            return mSimpleExoPlayer.getCurrentPosition();
        }
        return -1;
    }
    public long getDuration(){
        if (mSimpleExoPlayer != null){
            return mSimpleExoPlayer.getDuration();
        }
        return -1;
    }

    public boolean isPlaying(){
        if (mSimpleExoPlayer != null){
            return mSimpleExoPlayer.getPlaybackState() == Player.STATE_READY && mSimpleExoPlayer.getPlayWhenReady();
        }
        return false;
    }

    private void initializePlayer(){
        if (mSimpleExoPlayer == null){
            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
            RenderersFactory renderersFactory = buildRenderersFactory(true);

            DefaultTrackSelector.ParametersBuilder builder =
                    new DefaultTrackSelector.ParametersBuilder(/* context= */ mContext);
            trackSelectorParameters = builder.build();
            trackSelector = new DefaultTrackSelector(/* context= */ mContext, trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            mSimpleExoPlayer = new SimpleExoPlayer.Builder(/* context= */ mContext, renderersFactory)
                    .setTrackSelector(trackSelector)
                    .build();

            mSimpleExoPlayer.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
            mSimpleExoPlayer.setPlayWhenReady(true);
            mSimpleExoPlayer.addAnalyticsListener(new EventLogger(trackSelector));
            mSimpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playWhenReady && playbackState == Player.STATE_READY){
                        if (mPlayerView != null){
                            mPlayerView.setVisibility(VISIBLE);
                        }
                    }
                }
            });
            if (eventListeners != null && eventListeners.size() > 0){
                int i = 0;
                while (i < eventListeners.size()){
                    mSimpleExoPlayer.addListener(eventListeners.get(i));
                    i++;
                }
            }
            if (analyticsListeners != null && analyticsListeners.size() > 0){
                int i = 0;
                while (i < analyticsListeners.size()){
                    mSimpleExoPlayer.addAnalyticsListener(analyticsListeners.get(i));
                    i++;
                }
            }
            mPlayerView.setPlayer(mSimpleExoPlayer);
        }
    }

    public void addEventListener(Player.EventListener listener){
        eventListeners.add(listener);
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.addListener(listener);
        }
    }

    public void removeEventListener(Player.EventListener listener){
        eventListeners.remove(listener);
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.removeListener(listener);
        }
    }

    public void addAnalyticsListener(AnalyticsListener listener){
        analyticsListeners.add(listener);
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.addAnalyticsListener(listener);
        }
    }

    public void removeAnalyticsListener(AnalyticsListener listener){
        analyticsListeners.remove(listener);
        if (mSimpleExoPlayer != null){
            mSimpleExoPlayer.removeAnalyticsListener(listener);
        }
    }

    private MediaSource createMediaSource(int videoId) {
        MediaSource mediaSource = null;
        try {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, "yourApplicationName"));
            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(videoId));
            RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(mContext);
            rawResourceDataSource.open(dataSpec);

            ProgressiveMediaSource.Factory factory = new ProgressiveMediaSource.Factory(dataSourceFactory);
            mediaSource = factory.createMediaSource(rawResourceDataSource.getUri());
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
        return mediaSource;
    }

    public RenderersFactory buildRenderersFactory(boolean preferExtensionRenderer) {
        @DefaultRenderersFactory.ExtensionRendererMode
        int extensionRendererMode =
                useExtensionRenderers()
                        ? (preferExtensionRenderer
                        ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
        return new DefaultRenderersFactory(/* context= */ mContext)
                .setExtensionRendererMode(extensionRendererMode);
    }

    public boolean useExtensionRenderers() {
        return "withExtensions".equals(BuildConfig.FLAVOR);
    }

    public void releasePlayer(){
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.setVisibility(INVISIBLE);
        }
        trackSelector = null;
        trackSelectorParameters = null;
    }
}
