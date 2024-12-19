package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.ExternalNavigatorImpl
import ru.practicum.android.diploma.data.FilterRepositoryImpl
import ru.practicum.android.diploma.data.LocationRepositoryImpl
import ru.practicum.android.diploma.data.datasourse.FilterStorage
import ru.practicum.android.diploma.data.datasourse.FilterStorageImpl
import ru.practicum.android.diploma.data.datasourse.LocationStorage
import ru.practicum.android.diploma.data.datasourse.LocationStorageImpl
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.convertor.FavoriteVacancyDbConvertor
import ru.practicum.android.diploma.data.mapper.CountryMapper
import ru.practicum.android.diploma.data.mapper.FilterMapper
import ru.practicum.android.diploma.data.mapper.IndustryMapper
import ru.practicum.android.diploma.data.mapper.LocationMapper
import ru.practicum.android.diploma.data.mapper.OptionMapper
import ru.practicum.android.diploma.data.mapper.RegionMapper
import ru.practicum.android.diploma.data.mapper.VacancyDetailsMapper
import ru.practicum.android.diploma.data.mapper.VacancyMapper
import ru.practicum.android.diploma.data.network.AuthorizationInterceptor
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.data.network.VacancyNetworkClient
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.api.LocationRepository
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

private const val HEAD_HUNTER_BASE_URL = "https://api.hh.ru"
private const val KEY_FILTERS = "filters"

val dataModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "database.db"
        ).fallbackToDestructiveMigration().build()

    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()
    }

    single<HeadHunterApiService> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(HEAD_HUNTER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApiService::class.java)
    }

    factory {
        VacancyMapper()
    }

    factory {
        VacancyDetailsMapper()
    }

    factory {
        OptionMapper()
    }

    factory {
        IndustryMapper()
    }

    factory {
        RegionMapper()
    }

    factory {
        CountryMapper(get())
    }

    factory {
        LocationMapper(
            countryMapper = get(),
            regionMapper = get()
        )
    }

    factory {
        FavoriteVacancyDbConvertor()
    }

    single {
        VacancyNetworkClient(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }

    single {
        androidContext()
            .getSharedPreferences(KEY_FILTERS, Context.MODE_PRIVATE)
    }

    factory {
        Gson()
    }

    single<FilterStorage> {
        FilterStorageImpl(
            sharedPreferences = get(),
            gson = get()
        )
    }

    single<FilterRepository> {
        FilterRepositoryImpl(
            filterStorage = get(),
            mapper = get()
        )
    }

    factory {
        FilterMapper(
            industryMapper = get(),
            locationMapper = get()
        )
    }

    single<LocationStorage> {
        LocationStorageImpl(
            gson = get(),
            sharedPreferences = get()
        )
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            mapper = get(),
            storage = get()
        )
    }
}
