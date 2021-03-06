PGDMP                         z           internet_banking    13.0    13.0 !    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    67449    internet_banking    DATABASE     m   CREATE DATABASE internet_banking WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
     DROP DATABASE internet_banking;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            ?           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            ?            1259    67463    balance    TABLE     X   CREATE TABLE public.balance (
    user_id integer NOT NULL,
    amount numeric(15,2)
);
    DROP TABLE public.balance;
       public         heap    postgres    false    3            ?            1259    67461    balance_user_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.balance_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.balance_user_id_seq;
       public          postgres    false    203    3            ?           0    0    balance_user_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.balance_user_id_seq OWNED BY public.balance.user_id;
          public          postgres    false    202            ?            1259    67476 
   operations    TABLE     ?   CREATE TABLE public.operations (
    id integer NOT NULL,
    type character varying,
    amount numeric(15,2),
    date timestamp without time zone,
    user_id integer NOT NULL
);
    DROP TABLE public.operations;
       public         heap    postgres    false    3            ?            1259    67474    operations_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.operations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.operations_id_seq;
       public          postgres    false    3    205            ?           0    0    operations_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.operations_id_seq OWNED BY public.operations.id;
          public          postgres    false    204            ?            1259    67485    operations_user_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.operations_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.operations_user_id_seq;
       public          postgres    false    3    205            ?           0    0    operations_user_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.operations_user_id_seq OWNED BY public.operations.user_id;
          public          postgres    false    206            ?            1259    67452    users    TABLE     S   CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false    3            ?            1259    67450    users_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    3    201            ?           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    200            3           2604    67466    balance user_id    DEFAULT     r   ALTER TABLE ONLY public.balance ALTER COLUMN user_id SET DEFAULT nextval('public.balance_user_id_seq'::regclass);
 >   ALTER TABLE public.balance ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    202    203    203            4           2604    67479    operations id    DEFAULT     n   ALTER TABLE ONLY public.operations ALTER COLUMN id SET DEFAULT nextval('public.operations_id_seq'::regclass);
 <   ALTER TABLE public.operations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            5           2604    67487    operations user_id    DEFAULT     x   ALTER TABLE ONLY public.operations ALTER COLUMN user_id SET DEFAULT nextval('public.operations_user_id_seq'::regclass);
 A   ALTER TABLE public.operations ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    206    205            2           2604    67455    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    200    201    201            ?          0    67463    balance 
   TABLE DATA           2   COPY public.balance (user_id, amount) FROM stdin;
    public          postgres    false    203            ?          0    67476 
   operations 
   TABLE DATA           E   COPY public.operations (id, type, amount, date, user_id) FROM stdin;
    public          postgres    false    205            ?          0    67452    users 
   TABLE DATA           )   COPY public.users (id, name) FROM stdin;
    public          postgres    false    201            ?           0    0    balance_user_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.balance_user_id_seq', 1, false);
          public          postgres    false    202            ?           0    0    operations_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.operations_id_seq', 12, true);
          public          postgres    false    204            ?           0    0    operations_user_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.operations_user_id_seq', 1, false);
          public          postgres    false    206            ?           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 2, true);
          public          postgres    false    200            9           2606    67468    balance balance_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.balance
    ADD CONSTRAINT balance_pkey PRIMARY KEY (user_id);
 >   ALTER TABLE ONLY public.balance DROP CONSTRAINT balance_pkey;
       public            postgres    false    203            ;           2606    67484    operations operations_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.operations DROP CONSTRAINT operations_pkey;
       public            postgres    false    205            7           2606    67460    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    201            <           2606    67469    balance balance_user_id_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.balance
    ADD CONSTRAINT balance_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 F   ALTER TABLE ONLY public.balance DROP CONSTRAINT balance_user_id_fkey;
       public          postgres    false    203    201    2871            =           2606    67495    operations fk_user_id    FK CONSTRAINT     t   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 ?   ALTER TABLE ONLY public.operations DROP CONSTRAINT fk_user_id;
       public          postgres    false    201    2871    205            ?      x?3?42?30?2?4? 3b???? '?I      ?   ?   x?u??? D?5T?b?c3|jI?u<????utGF?d??????q?/?0A!??"??a(E????Hzt??D?u??????V	??k???'F+?!???q?K??ƙ3?{??y????ϻ?B׺??????8F      ?   (   x?3?t??,)ʬTN????2??L?/R??/?????? ??	      !    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    67449    internet_banking    DATABASE     m   CREATE DATABASE internet_banking WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
     DROP DATABASE internet_banking;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            ?           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            ?            1259    67463    balance    TABLE     X   CREATE TABLE public.balance (
    user_id integer NOT NULL,
    amount numeric(15,2)
);
    DROP TABLE public.balance;
       public         heap    postgres    false    3            ?            1259    67461    balance_user_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.balance_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.balance_user_id_seq;
       public          postgres    false    203    3            ?           0    0    balance_user_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.balance_user_id_seq OWNED BY public.balance.user_id;
          public          postgres    false    202            ?            1259    67476 
   operations    TABLE     ?   CREATE TABLE public.operations (
    id integer NOT NULL,
    type character varying,
    amount numeric(15,2),
    date timestamp without time zone,
    user_id integer NOT NULL
);
    DROP TABLE public.operations;
       public         heap    postgres    false    3            ?            1259    67474    operations_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.operations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.operations_id_seq;
       public          postgres    false    3    205            ?           0    0    operations_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.operations_id_seq OWNED BY public.operations.id;
          public          postgres    false    204            ?            1259    67485    operations_user_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.operations_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.operations_user_id_seq;
       public          postgres    false    3    205            ?           0    0    operations_user_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.operations_user_id_seq OWNED BY public.operations.user_id;
          public          postgres    false    206            ?            1259    67452    users    TABLE     S   CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false    3            ?            1259    67450    users_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    3    201            ?           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    200            3           2604    67466    balance user_id    DEFAULT     r   ALTER TABLE ONLY public.balance ALTER COLUMN user_id SET DEFAULT nextval('public.balance_user_id_seq'::regclass);
 >   ALTER TABLE public.balance ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    202    203    203            4           2604    67479    operations id    DEFAULT     n   ALTER TABLE ONLY public.operations ALTER COLUMN id SET DEFAULT nextval('public.operations_id_seq'::regclass);
 <   ALTER TABLE public.operations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            5           2604    67487    operations user_id    DEFAULT     x   ALTER TABLE ONLY public.operations ALTER COLUMN user_id SET DEFAULT nextval('public.operations_user_id_seq'::regclass);
 A   ALTER TABLE public.operations ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    206    205            2           2604    67455    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    200    201    201            ?          0    67463    balance 
   TABLE DATA           2   COPY public.balance (user_id, amount) FROM stdin;
    public          postgres    false    203   ?       ?          0    67476 
   operations 
   TABLE DATA           E   COPY public.operations (id, type, amount, date, user_id) FROM stdin;
    public          postgres    false    205   $        ?          0    67452    users 
   TABLE DATA           )   COPY public.users (id, name) FROM stdin;
    public          postgres    false    201   ?        ?           0    0    balance_user_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.balance_user_id_seq', 1, false);
          public          postgres    false    202            ?           0    0    operations_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.operations_id_seq', 12, true);
          public          postgres    false    204            ?           0    0    operations_user_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.operations_user_id_seq', 1, false);
          public          postgres    false    206            ?           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 2, true);
          public          postgres    false    200            9           2606    67468    balance balance_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.balance
    ADD CONSTRAINT balance_pkey PRIMARY KEY (user_id);
 >   ALTER TABLE ONLY public.balance DROP CONSTRAINT balance_pkey;
       public            postgres    false    203            ;           2606    67484    operations operations_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.operations DROP CONSTRAINT operations_pkey;
       public            postgres    false    205            7           2606    67460    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    201            <           2606    67469    balance balance_user_id_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.balance
    ADD CONSTRAINT balance_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 F   ALTER TABLE ONLY public.balance DROP CONSTRAINT balance_user_id_fkey;
       public          postgres    false    203    201    2871            =           2606    67495    operations fk_user_id    FK CONSTRAINT     t   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 ?   ALTER TABLE ONLY public.operations DROP CONSTRAINT fk_user_id;
       public          postgres    false    201    2871    205           