// Generated by view binder compiler. Do not edit!
package com.expressweather.accurate.live.weather.forecast.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.expressweather.accurate.live.weather.forecast.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import pl.droidsonroids.gif.GifImageView;

public final class ActivitySearchBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final GifImageView imgLoading;

  @NonNull
  public final ImageView imgNotFindLocation;

  @NonNull
  public final ConstraintLayout layoutLoading;

  @NonNull
  public final ConstraintLayout layoutNotFindLocation;

  @NonNull
  public final RelativeLayout rlSearch;

  @NonNull
  public final RecyclerView rvSearch;

  @NonNull
  public final SearchView searchView;

  @NonNull
  public final TextView tvNotFindLocation;

  @NonNull
  public final View viewHideKB;

  private ActivitySearchBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imgBack,
      @NonNull GifImageView imgLoading, @NonNull ImageView imgNotFindLocation,
      @NonNull ConstraintLayout layoutLoading, @NonNull ConstraintLayout layoutNotFindLocation,
      @NonNull RelativeLayout rlSearch, @NonNull RecyclerView rvSearch,
      @NonNull SearchView searchView, @NonNull TextView tvNotFindLocation,
      @NonNull View viewHideKB) {
    this.rootView = rootView;
    this.imgBack = imgBack;
    this.imgLoading = imgLoading;
    this.imgNotFindLocation = imgNotFindLocation;
    this.layoutLoading = layoutLoading;
    this.layoutNotFindLocation = layoutNotFindLocation;
    this.rlSearch = rlSearch;
    this.rvSearch = rvSearch;
    this.searchView = searchView;
    this.tvNotFindLocation = tvNotFindLocation;
    this.viewHideKB = viewHideKB;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgBack;
      ImageView imgBack = ViewBindings.findChildViewById(rootView, id);
      if (imgBack == null) {
        break missingId;
      }

      id = R.id.imgLoading;
      GifImageView imgLoading = ViewBindings.findChildViewById(rootView, id);
      if (imgLoading == null) {
        break missingId;
      }

      id = R.id.imgNotFindLocation;
      ImageView imgNotFindLocation = ViewBindings.findChildViewById(rootView, id);
      if (imgNotFindLocation == null) {
        break missingId;
      }

      id = R.id.layoutLoading;
      ConstraintLayout layoutLoading = ViewBindings.findChildViewById(rootView, id);
      if (layoutLoading == null) {
        break missingId;
      }

      id = R.id.layoutNotFindLocation;
      ConstraintLayout layoutNotFindLocation = ViewBindings.findChildViewById(rootView, id);
      if (layoutNotFindLocation == null) {
        break missingId;
      }

      id = R.id.rlSearch;
      RelativeLayout rlSearch = ViewBindings.findChildViewById(rootView, id);
      if (rlSearch == null) {
        break missingId;
      }

      id = R.id.rvSearch;
      RecyclerView rvSearch = ViewBindings.findChildViewById(rootView, id);
      if (rvSearch == null) {
        break missingId;
      }

      id = R.id.searchView;
      SearchView searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      id = R.id.tvNotFindLocation;
      TextView tvNotFindLocation = ViewBindings.findChildViewById(rootView, id);
      if (tvNotFindLocation == null) {
        break missingId;
      }

      id = R.id.viewHideKB;
      View viewHideKB = ViewBindings.findChildViewById(rootView, id);
      if (viewHideKB == null) {
        break missingId;
      }

      return new ActivitySearchBinding((ConstraintLayout) rootView, imgBack, imgLoading,
          imgNotFindLocation, layoutLoading, layoutNotFindLocation, rlSearch, rvSearch, searchView,
          tvNotFindLocation, viewHideKB);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
