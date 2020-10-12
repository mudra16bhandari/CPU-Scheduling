package com.example.scheduling.util;

import com.example.scheduling.model.Input;

public class MergeSort {

    //DOES'NT SORT THE ORIGINAL ARRAY, RATHER RETURN A ARRAY CONTAINING POSITIONS OF SORTED ARRAY

    //MERGE FUNCTION
    private static void merge(Input[] arr, int[] sort, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        System.arraycopy(sort, l, L, 0, n1);
        System.arraycopy(sort,m+1,R,0,n2);
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (arr[L[i]].compareTo(arr[R[j]]) <= 0) {
                sort[k] = L[i];
                i++;
            } else {
                sort[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            sort[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            sort[k] = R[j];
            j++;
            k++;
        }
    }

    //RECURSIVE FUNCTION
    private static void sort(Input[] arr, int[] sort, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, sort, l, m);
            sort(arr, sort, m + 1, r);
            merge(arr, sort, l, m, r);
        }
    }

    //ACTUAL FUNCTION TO USE
    public static int[] sort(Input[] arr) {
        int len = arr.length;
        int[] out = new int[len];
        for (int i = 0; i < len; i++)
            out[i] = i;
        sort(arr, out, 0, len - 1);
        return out;
    }
}
