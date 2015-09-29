package com.nearucenterplaza.redenvelopeassistant.service.core;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.nearucenterplaza.redenvelopeassistant.R;

public class SoundHelper {
	private SoundPool mSoundPool;
	private int soundIdRedEnvelopeComing = 0;
	
	public SoundHelper(Context context){
        mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        soundIdRedEnvelopeComing = mSoundPool.load(context, R.raw.red_envelope_coming, 1);
	}
	
    public void playSoundRedEnvelopeComing(){
    	mSoundPool.play(soundIdRedEnvelopeComing, 1, 1, 0, 0, 1);
    }
}
