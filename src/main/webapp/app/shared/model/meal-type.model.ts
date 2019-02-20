import { IRecipee } from 'app/shared/model/recipee.model';

export interface IMealType {
    id?: number;
    name?: string;
    mealTypeRecipees?: IRecipee[];
}

export class MealType implements IMealType {
    constructor(public id?: number, public name?: string, public mealTypeRecipees?: IRecipee[]) {}
}
