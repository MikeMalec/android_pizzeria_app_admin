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
public final class GetBeverages_Factory implements Factory<GetBeverages> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public GetBeverages_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public GetBeverages get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static GetBeverages_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new GetBeverages_Factory(productRemoteDataSourceProvider);
  }

  public static GetBeverages newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new GetBeverages(productRemoteDataSource);
  }
}
