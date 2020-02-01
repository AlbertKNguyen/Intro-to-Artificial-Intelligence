//
// Pfour
//
// This class provides a "main" method that acts as a driver program for
// a very simple "batch mode" backpropagtion artificial neural network.
//
// David Noelle -- Tue Nov 20 21:08:51 PST 2012
//

import java.io.*;
import java.util.*;

public class Pfour {

	public static void main(String[] args) {
		try {
			BP backprop = new BP();
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			Scanner inScanner = new Scanner(in);
			int numInputUnits = 2;
			int numHiddenUnits = 2;
			int numOutputUnits = 1;
			double lRate = 0.1;
			double initialWeightRange = 1.0;
			int epochReportBlock = 10;
			int epochCriterion = 1000;
			double stoppingCriterion = 0.05;
			String trainingFilename;
			String testingFilename;

			System.out.println("BACKPROPAGATION LEARNING ALGORITHM");
			// Get network information ...
			System.out.println("Enter the number of input units:");
			
			numInputUnits = 2;

			System.out.println("Enter the number of hidden units:");

			numHiddenUnits = 2;

			System.out.println("Enter the number of output units:");

			numOutputUnits = 1;

			// Get training parameter information ...
			System.out.println("Enter the learning rate:");

			lRate = 0.9;

			System.out.println("Enter the maximum number of training epochs:");

			epochCriterion = 10000;

			System.out.println("Enter the SSE stopping criterion:");

			stoppingCriterion = 0.001;

			// Get pattern set information ...

			trainingFilename = "xor.dat";

			testingFilename = "xor.dat";
			// Build the network ...
			Layer inLayer = backprop.net.createLayer(numInputUnits);
			Layer hidLayer = backprop.net.createLayer(numHiddenUnits);
			Layer outLayer = backprop.net.createLayer(numOutputUnits);
			if ((inLayer == null) || (hidLayer == null) || (outLayer == null)) {
				System.err.println("Unable to create layers.");
				return;
			}
			if ((backprop.net.createProjection(inLayer, hidLayer) == null)
					|| (backprop.net.createProjection(hidLayer, outLayer) == null)) {
				System.err.println("Unable to create projections.");
				return;
			}
			// Read the pattern sets ...
			if (!(backprop.readTrainingPatterns(trainingFilename))) {
				System.err.println("Unable to read training pattern file.");
				return;
			}
			if (!(backprop.readTestingPatterns(testingFilename))) {
				System.err.println("Unable to read testing pattern file.");
				return;
			}
			// Set learning parameters ...
			backprop.setLearningRate(lRate);
			backprop.setInitialWeightRange(initialWeightRange);
			// Perform training ...
			double thisSSE = stoppingCriterion + 1.0;
			backprop.initNetwork();
			while ((backprop.epochs < epochCriterion) && (thisSSE > stoppingCriterion)) {
				thisSSE = backprop.runTrainingEpoch();
				if (thisSSE < 0.0) {
					System.err.println("Training epoch failed.");
					return;
				}
				if (backprop.epochs % epochReportBlock == 0) {
					// Report on training progress ...
					System.out.println(
							"Epoch " + String.valueOf(backprop.epochs) + ":  SSE = " + String.valueOf(thisSSE) + ".");
				}
			}
			System.out.println(
					"Final Epoch " + String.valueOf(backprop.epochs) + ":  SSE = " + String.valueOf(thisSSE) + ".");
			// Perform testing ...
			System.out.println();
			thisSSE = backprop.runTestingEpoch(System.out);
			if (thisSSE < 0.0) {
				System.err.println("Testing epoch failed.");
				return;
			}
			System.out.println("Testing SSE = " + String.valueOf(thisSSE) + ".");
			System.out.println();
			// Done ...
			System.out.println("ARTIFICIAL NEURAL NETWORK RUN COMPLETE");
		} catch (Exception e) {
			// Something went wrong ...
		}
	}

}
