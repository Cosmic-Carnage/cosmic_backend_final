package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.betting.Betting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/sort")
public class CarApiController {

    // Sorting algorithms base class
    abstract static class SortingAlgorithm {
        abstract void sort(int[] arr);
    }

    // Merge sort
    static class MergeSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            Arrays.sort(arr);
        }
    }

    // Insertion sort
    static class InsertionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;

                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = key;
            }
        }
    }

    // Bubble sort
    static class BubbleSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }

    // Selection sort
    static class SelectionSort extends SortingAlgorithm {
        @Override
        void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    @GetMapping("/speeds")
    public Map<String, Integer> getAlgorithmSpeeds(@RequestParam(required = false) Integer arraySize) {
        int size = (arraySize != null && arraySize > 0) ? arraySize : 40000;

        int[] randomArray = generateRandomArray(size);

        Map<String, Integer> algorithmSpeeds = new HashMap<>();

        algorithmSpeeds.put("mergeSort", measureSortingSpeed(new MergeSort(), randomArray.clone()));
        algorithmSpeeds.put("insertionSort", measureSortingSpeed(new InsertionSort(), randomArray.clone()));
        algorithmSpeeds.put("bubbleSort", measureSortingSpeed(new BubbleSort(), randomArray.clone()));
        algorithmSpeeds.put("selectionSort", measureSortingSpeed(new SelectionSort(), randomArray.clone()));

        return algorithmSpeeds;
    }

    private int[] generateRandomArray(int size) {
        int[] randomArray = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(100);
        }
        return randomArray;
    }

    private void runSortingAlgorithm(SortingAlgorithm algorithm, int[] arr) {
        algorithm.sort(arr);
    }

    private int measureSortingSpeed(SortingAlgorithm algorithm, int[] arr) {
        long startTime = System.currentTimeMillis();
        runSortingAlgorithm(algorithm, arr);
        long endTime = System.currentTimeMillis();
        return (int) (endTime - startTime);
    }

@GetMapping("/bet")

public Map<String, Object> betOnSortRace(@RequestParam(required = true) int betAmount,
                                         @RequestParam(required = true) int startingPoints) {
    Betting game = new Betting(startingPoints);

    Map<String, Integer> speeds = getAlgorithmSpeeds(null);

    // Randomly select an algorithm
    List<String> algorithms = new ArrayList<>(speeds.keySet());
    String selectedAlgorithm = algorithms.get(new Random().nextInt(algorithms.size()));

    String fastestAlgorithm = speeds.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("none");

    boolean isGuessCorrect = selectedAlgorithm.equals(fastestAlgorithm);

    game.placeBet(betAmount, isGuessCorrect);

    Map<String, Object> result = new HashMap<>();
    result.put("message", game.getResultMessage());
    result.put("newScore", game.getPoints());
    result.put("selectedAlgorithm", selectedAlgorithm); // Include the selected algorithm in the response
    return result;
}

}