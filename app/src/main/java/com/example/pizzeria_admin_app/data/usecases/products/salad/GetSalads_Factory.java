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
public final class GetSalads_Factory implements Factory<GetSalads> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public GetSalads_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public GetSalads get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static GetSalads_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new GetSalads_Factory(productRemoteDataSourceProvider);
  }

  public static GetSalads newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new GetSalads(productRemoteDataSource);
  }
}
