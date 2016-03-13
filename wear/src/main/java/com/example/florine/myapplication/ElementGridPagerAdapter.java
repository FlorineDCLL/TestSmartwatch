package com.example.florine.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florine on 3/13/16.
 */
public class ElementGridPagerAdapter extends FragmentGridPagerAdapter {

    private List<Row> mRows = new ArrayList<Row>();
    private List<Element> mElements;

    public ElementGridPagerAdapter(List<Element> elements, FragmentManager fm){
        super(fm);
        this.mElements = elements;

        for(Element element : elements){
            mRows.add(new Row(CardFragment.create(element.getTitre(), element.getTexte())));
        }
    }


    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }


    @Override
    public Drawable getBackgroundForRow(final int row){
        return new ColorDrawable(mElements.get(row).getColor());
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column){
        return getBackgroundForRow(row);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }

    private class Row{
        final List<Fragment> columns = new ArrayList<Fragment>();
        public Row(Fragment... fragments){
            for(Fragment f : fragments)
                add(f);
        }
        public void add(Fragment f){
            columns.add(f);
        }
        public Fragment getColumn(int i){
            return columns.get(i);
        }
        public int getColumnCount(){
            return columns.size();
        }
    }
}
