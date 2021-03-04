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
public final class DeleteBeverage_Factory implements Factory<DeleteBeverage> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public DeleteBeverage_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public DeleteBeverage get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static DeleteBeverage_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new DeleteBeverage_Factory(productRemoteDataSourceProvider);
  }

  public static DeleteBeverage newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new DeleteBeverage(productRemoteDataSource);
  }
}
