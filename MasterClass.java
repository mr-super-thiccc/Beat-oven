import java.util.Scanner;

import jm.JMC;
import jm.music.data.*;
import jm.midi.*;
import jm.music.tools.*;
import jm.util.*;

public final class MasterClass implements JMC 
{
	private static Score masterScore = new Score("Drunk walk demo");  //creates the score that will contain all of the instruments (Parts) for a given song	
	
	
	
	private static Part partOne = new Part("Bass", STRING_ENSEMBLE_2, 0);  //creates a harp
	private static Phrase phraseOne = new Phrase();
	
	private static Part partTwo = new Part("OTHER", STRING_ENSEMBLE_2, 1);  //creates a string
	private static Phrase phraseTwo = new Phrase();
	
	private static Part partThree = new Part("ANOTHER", STRING_ENSEMBLE_2, 2);  //creates a synth
	private static Phrase phraseThree = new Phrase();
	
	
	
	public static double getMinValue(double[] array)
	{
		double minValue = array[0];
		for(int i=1; i < array.length; i++)
		{
			if(array[i] < minValue)
			{
				minValue = array[i];
			}
		}
		return minValue;
	}
	
	
	public static Phrase createMelody(int n_measures, double[] r_vals, Phrase phr)
	{
		int[] pitchArrayOne = {A4, C4, D4, E4, G4};
		//pitch array used for this particular melody
		
		double checker = 0;  //this will ensure that the notes that are added to a particular phrase do not exceed the specified number of measures
		
		while(checker < n_measures*4)
		{
			if(checker == n_measures*4 || (4*n_measures - checker < getMinValue(r_vals)))  //if the checker is the number of notes desired
				{
					break;  //finish the statement
				}
			else
				{
					double rand_val = r_vals[(int)(Math.random()*r_vals.length)];  //choose a random rhytm from the set of rhythmic values
					if(checker + rand_val <= n_measures*4)  //if the sum of the rand_val and the current checker does not exceed the number of measures wanted 
						{
							checker += rand_val;  //add the rand_val to checker
							
							int pitch_index = (int) (Math.random()*pitchArrayOne.length);
							int note = pitchArrayOne[pitch_index];  //select a random pitch from the array
							
							Note noteobj = new Note(note, rand_val, P);  //create a new note with the randomly generated note and rhythm
							phr.addNote(noteobj);  //add this note to the phrase
						}
				}
		}
		
		return phr;
		//the issue that this method has is that there is no notion of what rhythmic value should go before and after which other one
		//Markov chaining can simulate the decision that musicians make when deciding to create a random note 
		//Genetic algorithms can "learn", depending on various inputs, what to create
			//namely, Hierarchical Temporal Memory (HTM)
		//Beathoven will be a system that, learning from various different input (where to get this input from?, will generate ambient music
		
		//HTM to learn the various inputs, genetic algorithms and Markov chaining to produce the various sounds in a systematic way	
	}
	
	public static Phrase createOther(int n_measures, double[] r_vals, Phrase phr)
	{
		int[] pitchArrayOne = {A3, B3, C3, D3, E3, F3, G3};
		//pitch array used for this particular part
		
		double checker = 0;
		
		while(checker < n_measures*4)
		{
			if(checker == n_measures*4)
			{
				break;
			}
			else
			{
				double rand_val = r_vals[(int)(Math.random()*r_vals.length)];
				if(checker + rand_val <= n_measures*4)
				{
					checker += rand_val;
					int pitch_index = (int) (Math.random()*pitchArrayOne.length);
					int note = pitchArrayOne[pitch_index];
					
					Note noteobj = new Note(note, rand_val, P);
					phr.addNote(noteobj);
					
				}
			}
		}
		
		return phr;
		
	}
	
	public static Phrase createAnother(int n_measures, double[] r_vals, Phrase phr)
	{
		int[] pitchArrayOne = {A4, C4, D4, E4, G4};
		//pitch array used for this particular part
		
		double checker = 0;
		
		while(checker < n_measures*4)
		{
			if(checker == n_measures*4)
			{
				break;
			}
			else
			{
				double rand_val = r_vals[(int)(Math.random()*r_vals.length)];
				if(checker + rand_val <= n_measures*4)
				{
					checker += rand_val;
					int pitch_index = (int) (Math.random()*pitchArrayOne.length);
					int note = pitchArrayOne[pitch_index];
					
					Note noteobj = new Note(note, rand_val, P);
					phr.addNote(noteobj);
				}
			}
		}
		
		return phr;
	}
	
	
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the number of measures you want.");
		int n = reader.nextInt();
		//Determines how many measures should be created
		//simple math can translate this and the tempo to the estimated number of minutes needed
		
		
		double[] rhythmArray = {0.08333333333333333, 0.125, 0.16666666666666666, 0.25, 0.3333333333333333, 0.375, 0.5, 0.6666666666666666, 0.75, 0.875, 1.0, 1.3333333333333333, 1.5, 1.75, 2.0, 3.0, 3.5, 4.0}; /*THIRTYSECOND_NOTE};*/
		partOne.addPhrase(createMelody(n, rhythmArray, phraseOne));
		
		double[] rhythmArrayTwo = {HALF_NOTE, QUARTER_NOTE, EIGHTH_NOTE, SIXTEENTH_NOTE, THIRTYSECOND_NOTE};
		partTwo.addPhrase(createOther(n, rhythmArrayTwo, phraseTwo));
		
		double[] rhythmArrayThree = {HALF_NOTE, QUARTER_NOTE, EIGHTH_NOTE, SIXTEENTH_NOTE, THIRTYSECOND_NOTE}; /*THIRTYSECOND_NOTE};*/
		partThree.addPhrase(createAnother(n, rhythmArrayThree, phraseThree));

		
		masterScore.addPart(partOne);
		masterScore.addPart(partTwo);
		masterScore.addPart(partThree);
		//The question is how many instruments (parts) should be used by the system
		
		
		masterScore.setTempo(80);
		View.notate(masterScore);  //displays the score generated by the system
		Play.midi(masterScore);  //plays the score
	}
	
	
}