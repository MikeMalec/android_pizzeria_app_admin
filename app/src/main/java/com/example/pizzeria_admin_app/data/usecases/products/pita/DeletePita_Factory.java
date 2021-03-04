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
public final class DeletePita_Factory implements Factory<DeletePita> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public DeletePita_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public DeletePita get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static DeletePita_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new DeletePita_Factory(productRemoteDataSourceProvider);
  }

  public static DeletePita newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new DeletePita(productRemoteDataSource);
  }
}
