package ru.hofftech.parcellogistic.repository;

import ru.hofftech.parcellogistic.exception.DuplicateParcelException;
import ru.hofftech.parcellogistic.exception.ParcelAlreadyExistsException;
import ru.hofftech.parcellogistic.exception.ParcelNotFoundException;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.storage.FileParcelStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParcelRepository {

    private final List<ParcelEntity> parcels;

    public ParcelRepository(FileParcelStorage fileParcelStorage) {
        parcels = new ArrayList<>(fileParcelStorage.get());
    }

    public List<ParcelEntity> getList() {
        return parcels;
    }

    public Optional<ParcelEntity> getByName(String name) {
        return parcels.stream()
                .filter(parcel -> parcel.name().equals(name))
                .findFirst();
    }

    public Optional<ParcelEntity> getById(String id) {
        return parcels.stream()
                .filter(parcel -> parcel.id().equals(id))
                .findFirst();
    }

    public ParcelEntity create(ParcelEntity parcel) {
        ParcelEntity parcelWithId = ParcelEntity.builder()
                .id(parcel.name())
                .name(parcel.name())
                .content(parcel.content())
                .build();

        if (getById(parcelWithId.id()).isPresent()) {
            throw new ParcelAlreadyExistsException(parcelWithId.id());
        }

        parcels.add(parcelWithId);

        return parcelWithId;
    }

    public void delete(String id) {
        ParcelEntity parcel = getById(id).orElseThrow(() ->
                new ParcelNotFoundException(id)
        );

        parcels.remove(parcel);
    }

    public ParcelEntity update(String id, ParcelEntity parcel) {
        ParcelEntity oldParcel = getById(id).orElseThrow(() ->
                new ParcelNotFoundException(id)
        );

        if (oldParcel.equals(parcel)) {
            throw new DuplicateParcelException(oldParcel.id());
        }

        int index = parcels.indexOf(oldParcel);
        parcels.set(index, parcel);

        return parcel;
    }
}
