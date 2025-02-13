export class Page<EntityType> {
    content: EntityType[] = [];
    page: Pageable = new Pageable();
}

export class Pageable {
    number: number = 0;
    size: number = 0;
    totalElements: number = 0;
    totalPages: number = 0;
}
