package com.example.pizzeria_admin_app.data.usecases.products.pita;

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
public final class GetPitas_Factory implements Factory<GetPitas> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public GetPitas_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public GetPitas get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static GetPitas_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new GetPitas_Factory(productRemoteDataSourceProvider);
  }

  public static GetPitas newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new GetPitas(productRemoteDataSource);
  }
}
