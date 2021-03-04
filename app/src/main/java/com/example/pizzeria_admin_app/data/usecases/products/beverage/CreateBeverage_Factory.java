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
public final class CreateBeverage_Factory implements Factory<CreateBeverage> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public CreateBeverage_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public CreateBeverage get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static CreateBeverage_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new CreateBeverage_Factory(productRemoteDataSourceProvider);
  }

  public static CreateBeverage newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new CreateBeverage(productRemoteDataSource);
  }
}
