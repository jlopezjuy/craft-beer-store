package com.craftbeerstore.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.craftbeerstore.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Empresa.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Insumo.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Proveedor.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Presentacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Movimientos.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.DetalleMovimiento.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Caja.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Evento.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.EventoProducto.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Estilos.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Equipamiento.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.PuntoDeVenta.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.Receta.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.RecetaInsumo.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.InsumoRecomendado.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.CompraInsumo.class.getName(), jcacheConfiguration);
            cm.createCache(com.craftbeerstore.application.domain.CompraInsumoDetalle.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
