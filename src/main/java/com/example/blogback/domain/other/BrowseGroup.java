package com.example.blogback.domain.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseGroup {

    private String date;

    private ArrayList<BrowseHistory> browseHistory;
}
