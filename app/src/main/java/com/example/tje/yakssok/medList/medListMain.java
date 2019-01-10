package com.example.tje.yakssok.medList;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.tje.yakssok.R;

public class medListMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_list);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        //viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);

        //프래그먼트 배열 - 만들어 놓은 프래그먼트를 차례대로 넣어 준다.
        Fragment[] arrFragments = new Fragment[2];
        arrFragments[0] = new medList1Fragment();
        arrFragments[1] = new medList2Fragment();

        //어답터 생성후 연결 - 배열을 인자로 추가해 준다.
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager (viewPager);
    }

    //pager adapter class(빨간줄에서 Alt+Enter키로 '구현')
    private class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;

        //생성자
        public MyPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        //Tab의 타이틀 설정
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "편의점 상비약";
                case 1:
                    return "응급처치";
                default:
                    return "";
            }
            //return super.getPageTitle(position);
        }
    }

}