package com.triple.mileage.service;

import com.triple.mileage.domain.Image;
import com.triple.mileage.domain.Review;
import com.triple.mileage.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    protected void saveAll(List<UUID> attachedPhotoIds, Review review) {
        List<Image> images = attachedPhotoIds.stream()
                .map((imageId) -> new Image(imageId, review))
                .collect(Collectors.toList());
        imageRepository.saveAll(images);
    }

    @Transactional
    protected void changeAll(List<UUID> attachedPhotoIds, Review review) {
        List<Image> oldImages = imageRepository.findAllByReview(review);
        List<Image> removeImages = oldImages.stream()
                .filter(image -> !attachedPhotoIds.contains(image.getId()))
                .collect(Collectors.toList());
        List<Image> newImages = attachedPhotoIds.stream()
                .filter(imageId -> oldImages.stream().noneMatch(image -> {
                    assert image.getId() != null;
                    return image.getId().equals(imageId);
                }))
                .map(imageId -> new Image(imageId, review))
                .collect(Collectors.toList());

        imageRepository.saveAll(newImages);
        imageRepository.deleteAllInBatch(removeImages);
    }

    @Transactional
    protected void deleteAll(UUID reviewId) {
        imageRepository.deleteAllBy(reviewId);
    }
}
