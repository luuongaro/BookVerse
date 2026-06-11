package com.grupo3.BookVerse.features.groups.groupProgress;

import java.util.UUID;

public interface GroupProgressService {

    GroupProgressResponseDto calculateProgress(UUID groupId);

}