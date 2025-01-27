package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.exception.ParcelNotFoundException;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.repository.ParcelRepository;

/**
 * Service for managing parcel-related operations.
 */
@AllArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    /**
     * Retrieves a parcel by its name.
     *
     * @param name the name of the parcel
     * @return the ParcelEntity corresponding to the given name
     * @throws ParcelNotFoundException if the parcel is not found
     */
    public ParcelEntity getParcelByName(String name) {
        return parcelRepository.getByName(name).orElseThrow(() ->
                new ParcelNotFoundException(name)
        );
    }

    /**
     * Creates a new parcel in the repository.
     *
     * @param parcel the {@link ParcelEntity} to be created
     * @return the created {@link ParcelEntity}
     */
    public ParcelEntity createParcel(ParcelEntity parcel) {
        return parcelRepository.create(parcel);
    }

    /**
     * Deletes a parcel by its name.
     *
     * @param name the name of the parcel to be deleted
     */
    public void deleteParcel(String name) {
        parcelRepository.delete(name);
    }

    /**
     * Updates an existing parcel by its ID.
     *
     * @param id     the ID of the parcel to update
     * @param parcel the updated {@link ParcelEntity} data
     * @return the updated {@link ParcelEntity}
     */
    public ParcelEntity updateParcel(String id, ParcelEntity parcel) {
        return parcelRepository.update(id, parcel);
    }
}