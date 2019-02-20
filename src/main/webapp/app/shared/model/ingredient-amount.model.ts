export interface IIngredientAmount {
    id?: number;
    ingredientAmount?: number;
    ingredientUnit?: string;
    recipeeName?: string;
    recipeeId?: number;
    ingredientName?: string;
    ingredientId?: number;
}

export class IngredientAmount implements IIngredientAmount {
    constructor(
        public id?: number,
        public ingredientAmount?: number,
        public ingredientUnit?: string,
        public recipeeName?: string,
        public recipeeId?: number,
        public ingredientName?: string,
        public ingredientId?: number
    ) {}
}
