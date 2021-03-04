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
public final class UpdatePasta_Factory implements Factory<UpdatePasta> {
  private final Provider<ProductRemoteDataSource> productRemoteDataSourceProvider;

  public UpdatePasta_Factory(Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    this.productRemoteDataSourceProvider = productRemoteDataSourceProvider;
  }

  @Override
  public UpdatePasta get() {
    return newInstance(productRemoteDataSourceProvider.get());
  }

  public static UpdatePasta_Factory create(
      Provider<ProductRemoteDataSource> productRemoteDataSourceProvider) {
    return new UpdatePasta_Factory(productRemoteDataSourceProvider);
  }

  public static UpdatePasta newInstance(ProductRemoteDataSource productRemoteDataSource) {
    return new UpdatePasta(productRemoteDataSource);
  }
}
