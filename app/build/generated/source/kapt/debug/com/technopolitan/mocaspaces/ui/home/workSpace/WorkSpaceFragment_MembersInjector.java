// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.home.workSpace;

import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter;
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory;
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper;
import com.technopolitan.mocaspaces.modules.ApiResponseModule;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import java.util.List;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class WorkSpaceFragment_MembersInjector implements MembersInjector<WorkSpaceFragment> {
  private final Provider<WorkSpaceAdapter> workSpaceAdapterProvider;

  private final Provider<ApiResponseModule<List<WorkSpaceMapper>>> workSpaceApiHandlerProvider;

  private final Provider<ApiResponseModule<String>> favouriteApiHandlerProvider;

  private final Provider<ViewModelFactory> viewModelFactoryProvider;

  public WorkSpaceFragment_MembersInjector(Provider<WorkSpaceAdapter> workSpaceAdapterProvider,
      Provider<ApiResponseModule<List<WorkSpaceMapper>>> workSpaceApiHandlerProvider,
      Provider<ApiResponseModule<String>> favouriteApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    this.workSpaceAdapterProvider = workSpaceAdapterProvider;
    this.workSpaceApiHandlerProvider = workSpaceApiHandlerProvider;
    this.favouriteApiHandlerProvider = favouriteApiHandlerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<WorkSpaceFragment> create(
      Provider<WorkSpaceAdapter> workSpaceAdapterProvider,
      Provider<ApiResponseModule<List<WorkSpaceMapper>>> workSpaceApiHandlerProvider,
      Provider<ApiResponseModule<String>> favouriteApiHandlerProvider,
      Provider<ViewModelFactory> viewModelFactoryProvider) {
    return new WorkSpaceFragment_MembersInjector(workSpaceAdapterProvider, workSpaceApiHandlerProvider, favouriteApiHandlerProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(WorkSpaceFragment instance) {
    injectWorkSpaceAdapter(instance, workSpaceAdapterProvider.get());
    injectWorkSpaceApiHandler(instance, workSpaceApiHandlerProvider.get());
    injectFavouriteApiHandler(instance, favouriteApiHandlerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceFragment.workSpaceAdapter")
  public static void injectWorkSpaceAdapter(WorkSpaceFragment instance,
      WorkSpaceAdapter workSpaceAdapter) {
    instance.workSpaceAdapter = workSpaceAdapter;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceFragment.workSpaceApiHandler")
  public static void injectWorkSpaceApiHandler(WorkSpaceFragment instance,
      ApiResponseModule<List<WorkSpaceMapper>> workSpaceApiHandler) {
    instance.workSpaceApiHandler = workSpaceApiHandler;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceFragment.favouriteApiHandler")
  public static void injectFavouriteApiHandler(WorkSpaceFragment instance,
      ApiResponseModule<String> favouriteApiHandler) {
    instance.favouriteApiHandler = favouriteApiHandler;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceFragment.viewModelFactory")
  public static void injectViewModelFactory(WorkSpaceFragment instance,
      ViewModelFactory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
