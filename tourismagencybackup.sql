PGDMP     2                    |            tourismagency    14.12    14.12 D    N           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            O           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            P           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            Q           1262    16865    tourismagency    DATABASE     X   CREATE DATABASE tourismagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';
    DROP DATABASE tourismagency;
                postgres    false            �            1259    16976    facility    TABLE     v   CREATE TABLE public.facility (
    facility_id integer NOT NULL,
    facility_name character varying(100) NOT NULL
);
    DROP TABLE public.facility;
       public         heap    postgres    false            �            1259    16975    facility_facility_id_seq    SEQUENCE     �   CREATE SEQUENCE public.facility_facility_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.facility_facility_id_seq;
       public          postgres    false    214            R           0    0    facility_facility_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.facility_facility_id_seq OWNED BY public.facility.facility_id;
          public          postgres    false    213            �            1259    17012    hostel_type    TABLE     z   CREATE TABLE public.hostel_type (
    hostel_type_id integer NOT NULL,
    hostel_type_name character varying NOT NULL
);
    DROP TABLE public.hostel_type;
       public         heap    postgres    false            �            1259    17011    hostel_type_hostel_type_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hostel_type_hostel_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.hostel_type_hostel_type_id_seq;
       public          postgres    false    216            S           0    0    hostel_type_hostel_type_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.hostel_type_hostel_type_id_seq OWNED BY public.hostel_type.hostel_type_id;
          public          postgres    false    215            �            1259    16926    hotel    TABLE       CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name character varying(100) NOT NULL,
    address character varying(150) NOT NULL,
    mail character varying(50) NOT NULL,
    phone_number character varying(20) NOT NULL,
    star integer NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    17179    hotel_facility    TABLE     h   CREATE TABLE public.hotel_facility (
    hotel_id integer NOT NULL,
    facility_id integer NOT NULL
);
 "   DROP TABLE public.hotel_facility;
       public         heap    postgres    false            �            1259    17247    hotel_hostel_type    TABLE     n   CREATE TABLE public.hotel_hostel_type (
    hotel_id integer NOT NULL,
    hostel_type_id integer NOT NULL
);
 %   DROP TABLE public.hotel_hostel_type;
       public         heap    postgres    false            �            1259    16925    hotel_hotel_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotel_hotel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hotel_hotel_id_seq;
       public          postgres    false    212            T           0    0    hotel_hotel_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.hotel_hotel_id_seq OWNED BY public.hotel.hotel_id;
          public          postgres    false    211            �            1259    17036    hotel_time_period    TABLE     �   CREATE TABLE public.hotel_time_period (
    hotel_time_period_id integer NOT NULL,
    hotel_id integer NOT NULL,
    start_date1 date NOT NULL,
    end_date1 date NOT NULL,
    start_date2 date NOT NULL,
    end_date2 date NOT NULL
);
 %   DROP TABLE public.hotel_time_period;
       public         heap    postgres    false            �            1259    17035 *   hotel_time_period_hotel_time_period_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotel_time_period_hotel_time_period_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE public.hotel_time_period_hotel_time_period_id_seq;
       public          postgres    false    218            U           0    0 *   hotel_time_period_hotel_time_period_id_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE public.hotel_time_period_hotel_time_period_id_seq OWNED BY public.hotel_time_period.hotel_time_period_id;
          public          postgres    false    217            �            1259    17233    reservation    TABLE     �  CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    hotel_id integer NOT NULL,
    customer_name character varying(100) NOT NULL,
    customer_mobile character varying(20) NOT NULL,
    child_count integer DEFAULT 0,
    adult_count integer DEFAULT 1,
    reservation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    total_price double precision
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    17232    reservation_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservation_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.reservation_reservation_id_seq;
       public          postgres    false    221            V           0    0    reservation_reservation_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.reservation_reservation_id_seq OWNED BY public.reservation.reservation_id;
          public          postgres    false    220            �            1259    17308    room    TABLE     _  CREATE TABLE public.room (
    room_id integer NOT NULL,
    hotel_id integer NOT NULL,
    hostel_type_id integer NOT NULL,
    room_type character varying(50) NOT NULL,
    time_period_id integer NOT NULL,
    adult_price numeric(10,2) NOT NULL,
    child_price numeric(10,2) NOT NULL,
    stock_count integer NOT NULL,
    bed_count integer NOT NULL,
    square_meters numeric(10,2) NOT NULL,
    tv character varying(5) NOT NULL,
    minibar character varying(5) NOT NULL,
    console character varying(5) NOT NULL,
    safe character varying(5) NOT NULL,
    projector character varying(5) NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    17307    room_room_id_seq    SEQUENCE     �   CREATE SEQUENCE public.room_room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.room_room_id_seq;
       public          postgres    false    224            W           0    0    room_room_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.room_room_id_seq OWNED BY public.room.room_id;
          public          postgres    false    223            �            1259    16904    user_    TABLE     �   CREATE TABLE public.user_ (
    user_id integer NOT NULL,
    user_name character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    user_password character varying(255) NOT NULL,
    user_role character varying(20) NOT NULL
);
    DROP TABLE public.user_;
       public         heap    postgres    false            �            1259    16903    user__user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user__user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.user__user_id_seq;
       public          postgres    false    210            X           0    0    user__user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.user__user_id_seq OWNED BY public.user_.user_id;
          public          postgres    false    209            �           2604    16979    facility facility_id    DEFAULT     |   ALTER TABLE ONLY public.facility ALTER COLUMN facility_id SET DEFAULT nextval('public.facility_facility_id_seq'::regclass);
 C   ALTER TABLE public.facility ALTER COLUMN facility_id DROP DEFAULT;
       public          postgres    false    214    213    214            �           2604    17015    hostel_type hostel_type_id    DEFAULT     �   ALTER TABLE ONLY public.hostel_type ALTER COLUMN hostel_type_id SET DEFAULT nextval('public.hostel_type_hostel_type_id_seq'::regclass);
 I   ALTER TABLE public.hostel_type ALTER COLUMN hostel_type_id DROP DEFAULT;
       public          postgres    false    215    216    216            �           2604    16929    hotel hotel_id    DEFAULT     p   ALTER TABLE ONLY public.hotel ALTER COLUMN hotel_id SET DEFAULT nextval('public.hotel_hotel_id_seq'::regclass);
 =   ALTER TABLE public.hotel ALTER COLUMN hotel_id DROP DEFAULT;
       public          postgres    false    212    211    212            �           2604    17039 &   hotel_time_period hotel_time_period_id    DEFAULT     �   ALTER TABLE ONLY public.hotel_time_period ALTER COLUMN hotel_time_period_id SET DEFAULT nextval('public.hotel_time_period_hotel_time_period_id_seq'::regclass);
 U   ALTER TABLE public.hotel_time_period ALTER COLUMN hotel_time_period_id DROP DEFAULT;
       public          postgres    false    217    218    218            �           2604    17236    reservation reservation_id    DEFAULT     �   ALTER TABLE ONLY public.reservation ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservation_reservation_id_seq'::regclass);
 I   ALTER TABLE public.reservation ALTER COLUMN reservation_id DROP DEFAULT;
       public          postgres    false    220    221    221            �           2604    17311    room room_id    DEFAULT     l   ALTER TABLE ONLY public.room ALTER COLUMN room_id SET DEFAULT nextval('public.room_room_id_seq'::regclass);
 ;   ALTER TABLE public.room ALTER COLUMN room_id DROP DEFAULT;
       public          postgres    false    224    223    224            �           2604    16907    user_ user_id    DEFAULT     n   ALTER TABLE ONLY public.user_ ALTER COLUMN user_id SET DEFAULT nextval('public.user__user_id_seq'::regclass);
 <   ALTER TABLE public.user_ ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    210    209    210            A          0    16976    facility 
   TABLE DATA           >   COPY public.facility (facility_id, facility_name) FROM stdin;
    public          postgres    false    214   �U       C          0    17012    hostel_type 
   TABLE DATA           G   COPY public.hostel_type (hostel_type_id, hostel_type_name) FROM stdin;
    public          postgres    false    216   _V       ?          0    16926    hotel 
   TABLE DATA           X   COPY public.hotel (hotel_id, hotel_name, address, mail, phone_number, star) FROM stdin;
    public          postgres    false    212   �V       F          0    17179    hotel_facility 
   TABLE DATA           ?   COPY public.hotel_facility (hotel_id, facility_id) FROM stdin;
    public          postgres    false    219   �W       I          0    17247    hotel_hostel_type 
   TABLE DATA           E   COPY public.hotel_hostel_type (hotel_id, hostel_type_id) FROM stdin;
    public          postgres    false    222   �W       E          0    17036    hotel_time_period 
   TABLE DATA           {   COPY public.hotel_time_period (hotel_time_period_id, hotel_id, start_date1, end_date1, start_date2, end_date2) FROM stdin;
    public          postgres    false    218   X       H          0    17233    reservation 
   TABLE DATA           �   COPY public.reservation (reservation_id, hotel_id, customer_name, customer_mobile, child_count, adult_count, reservation_date, total_price) FROM stdin;
    public          postgres    false    221   {X       K          0    17308    room 
   TABLE DATA           �   COPY public.room (room_id, hotel_id, hostel_type_id, room_type, time_period_id, adult_price, child_price, stock_count, bed_count, square_meters, tv, minibar, console, safe, projector) FROM stdin;
    public          postgres    false    224   �X       =          0    16904    user_ 
   TABLE DATA           T   COPY public.user_ (user_id, user_name, email, user_password, user_role) FROM stdin;
    public          postgres    false    210   1Y       Y           0    0    facility_facility_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.facility_facility_id_seq', 7, true);
          public          postgres    false    213            Z           0    0    hostel_type_hostel_type_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.hostel_type_hostel_type_id_seq', 7, true);
          public          postgres    false    215            [           0    0    hotel_hotel_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 103, true);
          public          postgres    false    211            \           0    0 *   hotel_time_period_hotel_time_period_id_seq    SEQUENCE SET     Y   SELECT pg_catalog.setval('public.hotel_time_period_hotel_time_period_id_seq', 42, true);
          public          postgres    false    217            ]           0    0    reservation_reservation_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 1, true);
          public          postgres    false    220            ^           0    0    room_room_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.room_room_id_seq', 16, true);
          public          postgres    false    223            _           0    0    user__user_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.user__user_id_seq', 4, true);
          public          postgres    false    209            �           2606    16981    facility facility_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.facility
    ADD CONSTRAINT facility_pkey PRIMARY KEY (facility_id);
 @   ALTER TABLE ONLY public.facility DROP CONSTRAINT facility_pkey;
       public            postgres    false    214            �           2606    17019    hostel_type hostel_type_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.hostel_type
    ADD CONSTRAINT hostel_type_pkey PRIMARY KEY (hostel_type_id);
 F   ALTER TABLE ONLY public.hostel_type DROP CONSTRAINT hostel_type_pkey;
       public            postgres    false    216            �           2606    17183 "   hotel_facility hotel_facility_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.hotel_facility
    ADD CONSTRAINT hotel_facility_pkey PRIMARY KEY (hotel_id, facility_id);
 L   ALTER TABLE ONLY public.hotel_facility DROP CONSTRAINT hotel_facility_pkey;
       public            postgres    false    219    219            �           2606    17251 (   hotel_hostel_type hotel_hostel_type_pkey 
   CONSTRAINT     |   ALTER TABLE ONLY public.hotel_hostel_type
    ADD CONSTRAINT hotel_hostel_type_pkey PRIMARY KEY (hotel_id, hostel_type_id);
 R   ALTER TABLE ONLY public.hotel_hostel_type DROP CONSTRAINT hotel_hostel_type_pkey;
       public            postgres    false    222    222            �           2606    16931    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    212            �           2606    17041 (   hotel_time_period hotel_time_period_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.hotel_time_period
    ADD CONSTRAINT hotel_time_period_pkey PRIMARY KEY (hotel_time_period_id);
 R   ALTER TABLE ONLY public.hotel_time_period DROP CONSTRAINT hotel_time_period_pkey;
       public            postgres    false    218            �           2606    17241    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    221            �           2606    17313    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    224            �           2606    16909    user_ user__pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.user_ DROP CONSTRAINT user__pkey;
       public            postgres    false    210            �           2606    17042 )   hotel_time_period fk_hotel_time_period_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_time_period
    ADD CONSTRAINT fk_hotel_time_period_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 S   ALTER TABLE ONLY public.hotel_time_period DROP CONSTRAINT fk_hotel_time_period_id;
       public          postgres    false    3481    212    218            �           2606    17189 .   hotel_facility hotel_facility_facility_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_facility
    ADD CONSTRAINT hotel_facility_facility_id_fkey FOREIGN KEY (facility_id) REFERENCES public.facility(facility_id);
 X   ALTER TABLE ONLY public.hotel_facility DROP CONSTRAINT hotel_facility_facility_id_fkey;
       public          postgres    false    3483    214    219            �           2606    17184 +   hotel_facility hotel_facility_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_facility
    ADD CONSTRAINT hotel_facility_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 U   ALTER TABLE ONLY public.hotel_facility DROP CONSTRAINT hotel_facility_hotel_id_fkey;
       public          postgres    false    212    3481    219            �           2606    17257 7   hotel_hostel_type hotel_hostel_type_hostel_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_hostel_type
    ADD CONSTRAINT hotel_hostel_type_hostel_type_id_fkey FOREIGN KEY (hostel_type_id) REFERENCES public.hostel_type(hostel_type_id) ON DELETE CASCADE;
 a   ALTER TABLE ONLY public.hotel_hostel_type DROP CONSTRAINT hotel_hostel_type_hostel_type_id_fkey;
       public          postgres    false    3485    222    216            �           2606    17252 1   hotel_hostel_type hotel_hostel_type_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_hostel_type
    ADD CONSTRAINT hotel_hostel_type_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 [   ALTER TABLE ONLY public.hotel_hostel_type DROP CONSTRAINT hotel_hostel_type_hotel_id_fkey;
       public          postgres    false    212    222    3481            �           2606    17242 %   reservation reservation_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 O   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_hotel_id_fkey;
       public          postgres    false    221    212    3481            �           2606    17319    room room_hostel_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_hostel_type_id_fkey FOREIGN KEY (hostel_type_id) REFERENCES public.hostel_type(hostel_type_id) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.room DROP CONSTRAINT room_hostel_type_id_fkey;
       public          postgres    false    3485    216    224            �           2606    17314    room room_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) ON DELETE CASCADE;
 A   ALTER TABLE ONLY public.room DROP CONSTRAINT room_hotel_id_fkey;
       public          postgres    false    212    3481    224            �           2606    17324    room room_time_period_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_time_period_id_fkey FOREIGN KEY (time_period_id) REFERENCES public.hotel_time_period(hotel_time_period_id) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.room DROP CONSTRAINT room_time_period_id_fkey;
       public          postgres    false    218    3487    224            A   g   x�%�=
�@�:�)r����8���H���DF�믰��e�E���հ!'w�(�I�3�N⭋���QWEE�^A�5}쒝[�J��G�3��vL	��c      C   r   x�3��))JTp��Q��K�)-�,K�2�D�s���*8�&f�%�p�p��Ux����p�rz$�)8�'�p�q���T*8��p�CT9��d� �H����Qp������ ��)"      ?   �   x�U�K�0��{�`������8q��I��0-$�U�f�`�As��͓�m�m����w�9���G�jelzik���� (Ő؄ī6���3θ���)�|�[8��x��E;+h�re1�lz7��<j4GY��k=�?-�r������1
�W��O���VCN�,,�ᜆe$�pԾ�=��{���'l��]2�@�P@�"��~k�      F      x����4䲴�4�240��@2F��� 8]�      I      x�340�4�2�f@�H��qqq )*9      E   L   x�e���P�3�)�����s���`����!J��`�È�4r���tq��3Y���s�ɞēĔ�����u��a��      H   >   x�3�440��,�M�6�416�4B##]3]#3##+Cs+#K=3CssNK�=... ;V
s      K   X   x�34�440�4���K�IU����41�46�30���PbWr�A ��)v�� eA��J�&3�&cN3TMFH���5��q��qqq ��"�      =   e   x�3��,�M�.I��̃0�s3s���s9��S���9]|=���9K+�J�����$�!`��z����T�� �R����?�/��ϝ+F��� Y�-n     