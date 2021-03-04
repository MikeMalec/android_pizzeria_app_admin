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
public final class DeletePasta_Factory implements Factory<DeletePasta> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public DeletePasta_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public DeletePasta get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static DeletePasta_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new DeletePasta_Factory(productRemoteDataSourceProvider);
  }

  public static DeletePasta newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new DeletePasta(productRemoteDataSource);
  }
}
