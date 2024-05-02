export interface Column {
    id: 'id' | 'name' | 'description' | 'price';
    label: string;
    minWidth?: number;
    align?: 'right';
    format?: (value: number) => string;
}
