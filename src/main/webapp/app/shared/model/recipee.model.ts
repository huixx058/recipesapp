import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IRecipee {
    id?: number;
    name?: string;
    description?: string;
    steps?: string;
    ingredients?: IIngredient[];
    mealTypeName?: string;
    mealTypeId?: number;
}

export class Recipee implements IRecipee {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public steps?: string,
        public ingredients?: IIngredient[],
        public mealTypeName?: string,
        public mealTypeId?: number
    ) {}
}
