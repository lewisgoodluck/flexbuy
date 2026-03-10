-- Create roles table directly in the default database (flexbuy)
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- Insert default roles (ignore if already exists)
INSERT INTO roles (name, description) VALUES
    ('ROLE_BUYER',  'Buyer role, can browse products and place orders')
ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name, description) VALUES
    ('ROLE_VENDOR', 'Vendor role, can list products and manage their catalog')
ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name, description) VALUES
    ('ROLE_ADMIN',  'Administrator role with full access to manage users, vendors, and system data')
ON CONFLICT (name) DO NOTHING;

--Create categories table directly in the default database (flexbuy)
CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL,
    category_description TEXT,
    parent_id BIGINT REFERENCES category(id) ON DELETE CASCADE
);


-- 15 Main Categories
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Electronics', 'Electronic devices and accessories', NULL),             -- id=1
('Fashion', 'Clothing and accessories', NULL),                           -- id=2
('Home & Kitchen', 'Home appliances and kitchenware', NULL),             -- id=3
('Beauty & Personal Care', 'Cosmetics and personal care products', NULL),-- id=4
('Sports & Outdoors', 'Sports equipment and outdoor gear', NULL),        -- id=5
('Books & Stationery', 'Books, magazines, and stationery items', NULL),  -- id=6
('Toys & Games', 'Toys, puzzles, and games', NULL),                      -- id=7
('Automotive', 'Car accessories and automotive parts', NULL),            -- id=8
('Health & Wellness', 'Healthcare products and supplements', NULL),      -- id=9
('Groceries', 'Food and beverage items', NULL),                          -- id=10
('Baby Products', 'Items for babies and toddlers', NULL),                -- id=11
('Pet Supplies', 'Food and accessories for pets', NULL),                 -- id=12
('Office Supplies', 'Office furniture and equipment', NULL),             -- id=13
('Jewelry & Watches', 'Fine jewelry and watches', NULL),                 -- id=14
('Footwear', 'Shoes, sandals, and sneakers', NULL);                      -- id=15

-- SubCategories
-- Electronics
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Smartphones', 'Mobile phones of all brands', 1),
('Laptops', 'All types of laptops', 1),
('Televisions', 'Smart and LED TVs', 1);

-- Fashion
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Men Clothing', 'Clothing for men', 2),
('Women Clothing', 'Clothing for women', 2),
('Accessories', 'Fashion accessories', 2);

-- Home & Kitchen
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Furniture', 'Home and office furniture', 3),
('Cookware', 'Kitchen utensils and cookware', 3),
('Appliances', 'Home appliances', 3);

-- Beauty & Personal Care
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Skincare', 'Creams and lotions', 4),
('Makeup', 'Cosmetics products', 4),
('Haircare', 'Shampoos and conditioners', 4);

-- Sports & Outdoors
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Fitness Equipment', 'Gym and exercise tools', 5),
('Outdoor Gear', 'Camping and hiking items', 5),
('Sportswear', 'Sports clothing and shoes', 5);

-- Books & Stationery
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Novels', 'Fiction and non-fiction novels', 6),
('Academic', 'School and college textbooks', 6),
('Office Stationery', 'Pens, notebooks, and paper', 6);

-- Toys & Games
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Action Figures', 'Collectible and play action figures', 7),
('Board Games', 'Board and card games', 7),
('Puzzles', 'Jigsaw and brain puzzles', 7);

-- Automotive
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Car Electronics', 'Stereos, GPS, and accessories', 8),
('Spare Parts', 'Replacement parts and components', 8),
('Car Care', 'Car cleaning and maintenance products', 8);

-- Health & Wellness
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Supplements', 'Vitamins and health supplements', 9),
('Medical Devices', 'Health monitoring devices', 9),
('Hygiene', 'Personal hygiene products', 9);

-- Groceries
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Beverages', 'Soft drinks, tea, and coffee', 10),
('Snacks', 'Chips, biscuits, and packaged snacks', 10),
('Fresh Produce', 'Vegetables, fruits, and greens', 10);

-- Baby Products
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Diapers', 'All sizes of baby diapers', 11),
('Baby Food', 'Baby formula and food items', 11),
('Toys', 'Baby toys and learning products', 11);

-- Pet Supplies
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Pet Food', 'Dog, cat, and other pet food', 12),
('Pet Toys', 'Toys for all types of pets', 12),
('Pet Grooming', 'Grooming and hygiene products for pets', 12);

-- Office Supplies
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Desks & Chairs', 'Office furniture items', 13),
('Printers', 'Office printers and accessories', 13),
('Stationery', 'Office stationery items', 13);

-- Jewelry & Watches
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Necklaces', 'Gold, silver, and diamond necklaces', 14),
('Rings', 'Engagement and fashion rings', 14),
('Watches', 'Luxury and casual watches', 14);

-- Footwear
INSERT INTO category (category_name, category_description, parent_id) VALUES
('Sneakers', 'Sports and casual sneakers', 15),
('Formal Shoes', 'Business and formal footwear', 15),
('Sandals', 'Casual sandals and flip-flops', 15);
