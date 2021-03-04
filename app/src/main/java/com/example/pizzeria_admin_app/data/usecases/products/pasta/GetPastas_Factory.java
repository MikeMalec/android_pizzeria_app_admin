package com.example.pizzeria_admin_app.data.usecases.products.pasta;

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
public final class GetPastas_Factory implements Factory<GetPastas> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public GetPastas_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public GetPastas get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static GetPastas_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new GetPastas_Factory(productRemoteDataSourceProvider);
  }

  public static GetPastas newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new GetPastas(productRemoteDataSource);
  }
}
