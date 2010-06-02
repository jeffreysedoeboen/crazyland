package model;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.*;

public class MidiPlayer implements MetaEventListener {

    public static final int END_OF_TRACK_MESSAGE = 47;

    private Sequencer sequencer;
    private boolean loop;
    private boolean paused;

    public MidiPlayer() {
//        try {
//            sequencer = MidiSystem.getSequencer();
//            sequencer.open();
//            sequencer.addMetaEventListener(this);
//        }
//        catch ( MidiUnavailableException ex) {
//            sequencer = null;
//        }
    }

    public Sequence getSequence(String filename) {
		return null;
//        try {
//            return MidiSystem.getSequence(new File(filename));
//        }
//        catch (InvalidMidiDataException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
    }

    public void play(Sequence sequence, boolean loop) {
//        if (sequencer != null && sequence != null) {
//            try {
//                sequencer.setSequence(sequence);
//                sequencer.start();
//                this.loop = loop;
//            }
//            catch (InvalidMidiDataException ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    public void meta(MetaMessage event) {
//        if (event.getType() == END_OF_TRACK_MESSAGE) {
//            if (sequencer != null && sequencer.isOpen() && loop) {
//            	sequencer.setTickPosition(0);
//                sequencer.start();
//            }
//        }
    }

    public void stop() {
//         if (sequencer != null && sequencer.isOpen()) {
//             sequencer.stop();
//             sequencer.setMicrosecondPosition(0);
//         }
    }

    public void close() {
//         if (sequencer != null && sequencer.isOpen()) {
//             sequencer.close();
//         }
    }

    public Sequencer getSequencer() {
		return sequencer;
//        return sequencer;
    }

    public void setPaused(boolean paused) {
//        if (this.paused != paused && sequencer != null) {
//            this.paused = paused;
//            if (paused) {
//                sequencer.stop();
//            }
//            else {
//                sequencer.start();
//            }
//        }
    }

    public boolean isPaused() {
		return loop;
//        return paused;
    }

}