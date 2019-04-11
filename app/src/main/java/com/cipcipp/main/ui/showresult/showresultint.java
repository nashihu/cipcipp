package com.cipcipp.main.ui.showresult;

import com.cipcipp.main.model.CellModel;

import java.util.ArrayList;
import java.util.List;

public interface showresultint {
    void onCallBack(ArrayList<String> strings, List<List<CellModel>> cellModels, ArrayList<String> rowNum);
}
