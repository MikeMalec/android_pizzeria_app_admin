package com.example.pizzeria_admin_app.data.usecases.products.salad;

import com.example.pizzeria_admin_app.data.remote.product.ProductRemoteDataSource;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class UpdateSalad_Factory implements Factory<UpdateSalad> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public UpdateSalad_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public UpdateSalad get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static UpdateSalad_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new UpdateSalad_Factory(productRemoteDataSourceProvider);
  }

  public static UpdateSalad newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new UpdateSalad(productRemoteDataSource);
  }
}
