package com.example.pizzeria_admin_app.data.usecases.products.beverage;

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
public final class UpdateBeverage_Factory implements Factory<UpdateBeverage> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public UpdateBeverage_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public UpdateBeverage get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static UpdateBeverage_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new UpdateBeverage_Factory(productRemoteDataSourceProvider);
  }

  public static UpdateBeverage newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new UpdateBeverage(productRemoteDataSource);
  }
}
