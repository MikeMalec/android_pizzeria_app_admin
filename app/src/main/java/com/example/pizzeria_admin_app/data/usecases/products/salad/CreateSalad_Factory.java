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
public final class CreateSalad_Factory implements Factory<CreateSalad> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public CreateSalad_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public CreateSalad get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static CreateSalad_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new CreateSalad_Factory(productRemoteDataSourceProvider);
  }

  public static CreateSalad newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new CreateSalad(productRemoteDataSource);
  }
}
