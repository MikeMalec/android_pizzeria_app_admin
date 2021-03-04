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
public final class CreatePita_Factory implements Factory<CreatePita> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public CreatePita_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public CreatePita get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static CreatePita_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new CreatePita_Factory(productRemoteDataSourceProvider);
  }

  public static CreatePita newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new CreatePita(productRemoteDataSource);
  }
}
