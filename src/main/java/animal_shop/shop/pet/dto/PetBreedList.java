package animal_shop.shop.pet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetBreedList {
    List<String> breeds;
}