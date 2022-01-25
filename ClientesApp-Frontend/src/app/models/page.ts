import { Pageable } from './pageable';
import { Sort } from './sort';

export interface Page<T> {
    content:          T[];
    pageable:         Pageable;
    last:             boolean;
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    sort:             Sort;
    first:            boolean;
    numberOfElements: number;
    empty:            boolean;
}