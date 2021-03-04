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
public final class DeleteSalad_Factory implements Factory<DeleteSalad> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public DeleteSalad_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public DeleteSalad get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static DeleteSalad_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new DeleteSalad_Factory(productRemoteDataSourceProvider);
  }

  public static DeleteSalad newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new DeleteSalad(productRemoteDataSource);
  }
}
