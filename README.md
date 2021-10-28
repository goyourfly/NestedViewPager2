# NestedViewPager2

The origin ViewPager2 doesn't support nested scroll as [said here](https://developer.android.com/training/animation/vp2-migration?hl=zh-cn#nested-scrollables), official solution is ugly you can [see here](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)

- This demo is reuse nested scroll api solved the nested scroll problem which make scroll most smooth

- The source code use ViewPager2 + HorizontalScrollView as demo to show how to make nested scroll work.