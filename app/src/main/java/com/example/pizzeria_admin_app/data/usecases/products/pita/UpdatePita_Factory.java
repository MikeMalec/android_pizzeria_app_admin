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
public final class UpdatePita_Factory implements Factory<UpdatePita> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public UpdatePita_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public UpdatePita get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static UpdatePita_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new UpdatePita_Factory(productRemoteDataSourceProvider);
  }

  public static UpdatePita newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new UpdatePita(productRemoteDataSource);
  }
}
