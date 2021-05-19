package com.cdxz.liudake.util;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.cdxz.liudake.LiudakeApplication;

import java.util.Locale;

public class TtsManager {
    private TtsManager() {
    }

    private static class SingletonHoler {
        public static final TtsManager INSTANCE = new TtsManager();
    }

    public static TtsManager getInstance() {
        return SingletonHoler.INSTANCE;
    }

    private TextToSpeech mSpeech;
    private boolean mIsInited;
    private UtteranceProgressListener mSpeedListener;

    public void init() {
        destory();
        mSpeech = new TextToSpeech(LiudakeApplication.getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = mSpeech.setLanguage(Locale.getDefault());
//                mSpeech.setPitch(1.0f); // 设置音调
//                mSpeech.setSpeechRate(1.5f); // 设置语速
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    //语音合成初始化失败,不支持语种
                    mIsInited = false;
                } else {
                    mIsInited = true;
                }
                mSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        //======语音合成 Start
                        if (mSpeedListener != null)
                            mSpeedListener.onStart(utteranceId);
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        //======语音合成 Done
                        if (mSpeedListener != null)
                            mSpeedListener.onDone(utteranceId);
                    }

                    @Override
                    public void onError(String utteranceId) {
                        //======语音合成 Error
                        if (mSpeedListener != null)
                            mSpeedListener.onError(utteranceId);
                    }
                });
            }
        });
    }

    public void setSpeechListener(UtteranceProgressListener listener) {
        this.mSpeedListener = listener;
    }

    public boolean speakText(String text) {
        if (!mIsInited) {
            //语音合成失败，未初始化成功
            init();
            return false;
        }
        if (mSpeech != null) {
            int result = mSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
            return result == TextToSpeech.SUCCESS;
        }
        return false;
    }

    public void stop() {
        if (mSpeech != null && mSpeech.isSpeaking()) {
            mSpeech.stop();
        }
    }

    public boolean isSpeaking() {
        if (mSpeech == null)
            return false;
        return mSpeech.isSpeaking();
    }


    public void destory() {
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
        }
    }
}